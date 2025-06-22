package com.github.elijahgabrielletanabe;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.ServiceLoader;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Controller implements Initializable
{
    @FXML private LineChart<String, Long> lineChart;
    
    @FXML private VBox sortPanel;
    @FXML private VBox statsPanel;
    @FXML private ScrollPane sortListContainer;
    @FXML private VBox statsContainer;
    @FXML private VBox sortList;

    @FXML private CategoryAxis x;
    @FXML private NumberAxis y;
    @FXML private Label statTitle;
    @FXML private Button runButton;

    private final HashMap<String, AlgorithmBase> algoList;
    private final ArrayList<AlgorithmBase> queueList;
    private final ObservableList<String> sortSizes;

    public Controller()
    {
        this.algoList = new HashMap<>();
        this.queueList = new ArrayList<>();
        this.sortSizes = FXCollections.observableList(new ArrayList<>());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        System.out.println("Running!");

        //# Apply Css to parent containers
        this.sortPanel.getStylesheets().add(getFileByString("LeftPanel.css", "css").toExternalForm());
        this.lineChart.getStylesheets().add(getFileByString("LineChart.css", "css").toExternalForm());
        this.statsPanel.getStylesheets().add(getFileByString("Stats.css", "css").toExternalForm());

        //# Apply Css classes
        this.sortList.getStyleClass().add("sort-list");
        this.statsPanel.getStyleClass().add("vbox");
        this.statTitle.getStyleClass().add("stat-title");
        this.statsContainer.getStyleClass().add("stats-container");
        this.runButton.getStyleClass().add("run-button");

        //# Find and load all algorithm files
        ServiceLoader<AlgorithmBase> loader = ServiceLoader.load(AlgorithmBase.class);
		
        for (AlgorithmBase algo : loader) 
		{
            algoList.put(algo.getClass().getSimpleName(), algo);
        }

        //# SortList/Container settings
        VBox.setVgrow(sortListContainer, Priority.ALWAYS);
        this.sortList.setPrefWidth(this.sortListContainer.getPrefWidth());
        this.sortList.setPrefHeight(Region.USE_COMPUTED_SIZE);
        this.sortListContainer.setVbarPolicy(ScrollBarPolicy.NEVER);
        
        //# Populate SortList
        for (String key : algoList.keySet())
        {
            //# Set up Button
            Button button = new Button(key);
            button.getStyleClass().add("sort-button");
            button.setCursor(Cursor.HAND);
            VBox.setVgrow(button, Priority.ALWAYS);

            //# Add Event Handler
            SortButtonEvent ae = new SortButtonEvent(this.queueList, algoList.get(key));
            button.setOnAction(ae);

            sortList.getChildren().add(button);
        }

        //# Have user specify sort sizes, into arraylist for usage in tests and GUI
 
        // Default sort size values
        this.sortSizes.addAll(new String[]{"100", "500", "1000", "5000", "10000"});

        this.x.setCategories(sortSizes);
    }

    @FXML
    //For loops galore
    public void runCalculations(MouseEvent event) throws InterruptedException 
    {
        Button button = (Button) event.getSource();
        button.setDisable(true);

        if (this.queueList.isEmpty())
        {
            button.setDisable(false);
            return;
        }

        ArrayList<Thread> spawnedThreads = new ArrayList<>();
        ArrayList<AlgorithmBase> currentQueueList = new ArrayList<>();
        for (AlgorithmBase ab : this.queueList) { currentQueueList.add(ab); }

        Thread side = new Thread(() -> {

            for (String s : this.sortSizes)
            {
                System.out.println("Sort Size: " + s);

                int sortSize = Integer.parseInt(s);

                //# Generate int array to sort
                ArrayList<Integer> toSort = new ArrayList<>();
                for (int i = 0; i < sortSize; i++) { toSort.add(i); }
                Collections.shuffle(toSort);

                for (AlgorithmBase ab : currentQueueList)
                {
                    //# Calculate with worker thread
                    Thread calcThread = new Thread(() -> 
					{
						//# Create deep copy of sort list for each algo
                        ArrayList<Integer> toSortDeepCopy = new ArrayList<>();

                        for (int i = 0; i < toSort.size(); i++)
                        {
                            toSortDeepCopy.add(Integer.valueOf(toSort.get(i)));
                        }

                        //System.out.println("Executing task on: " + Thread.currentThread().getName());

                        //# Run experiment
                        ab.experiment(toSortDeepCopy, sortSize);
						ab.verifySort(toSortDeepCopy);
                    });

                    spawnedThreads.add(calcThread);
					calcThread.start();
                }
				
				//# Wait for all threads to finish
                for (Thread thread : spawnedThreads)
                {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted Thread: " + thread.getName());
                    }
                }
				
                spawnedThreads.clear();
            }

            System.out.println("\tFinished Calculations!");

            Platform.runLater(() -> 
			{
				//# Clear all XYSeries on the chart
				this.lineChart.getData().clear();
				//# Clear all stats in stats panel
				this.statsContainer.getChildren().clear();

				for (AlgorithmBase ab : currentQueueList)
				{
					//# Displaying data
					displayLineChartData(ab);
					displayStatPaneData(ab);

					//# Cleaning up calculation data from ab
					ab.cleanUp();
				}
				
				button.setDisable(false);
            });
        });

        side.start();
    }

    private void displayLineChartData(AlgorithmBase ab)
    {
		//# Creating XYChart series to display on chart
        XYChart.Series<String, Long> chartDataSeries = new XYChart.Series<>();
		chartDataSeries.setName(ab.toString());

		//# Populating series with data from ab
		for (XYChart.Data<String, Long> cd : ab.getDataList())
		{
			chartDataSeries.getData().add(new XYChart.Data<>(cd.getXValue(), cd.getYValue()));
		}
		
		this.lineChart.getData().add(chartDataSeries);
    }
	
	private void displayStatPaneData(AlgorithmBase ab)
	{
		ArrayList<Long> computeTimes = ab.getComputeTimes();
		Collections.sort(computeTimes);
		
		//# Creating and populating stat card to display
		VBox statCard = new VBox();
		Label algorithm = new Label("Algorithm: " + ab.toString());
		Label maxTime = new Label("Slowest: " + computeTimes.get(computeTimes.size() - 1).toString() + " ms");
		Label minTime = new Label("Fastest: " + computeTimes.get(0).toString() + " ms");
		Label iterations = new Label("Iterations: " + Integer.toString(ab.getIterations()));
		Label timeComplexity = new Label("Complexity: " + ab.getTimeComplexity());
		
		// Stat card Css
		statCard.getStyleClass().add("stat-card");
		
		statCard.getChildren().addAll(algorithm, maxTime, minTime, iterations, timeComplexity);
		this.statsContainer.getChildren().add(statCard);
	}

    private URL getFileByString(String path, String type)
    {
        return this.getClass().getResource(type + "/" + path);
    }
}
