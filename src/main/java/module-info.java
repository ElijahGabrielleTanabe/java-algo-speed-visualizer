module com.github.elijahgabrielletanabe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.github.elijahgabrielletanabe to javafx.fxml;
    exports com.github.elijahgabrielletanabe;
    exports com.github.elijahgabrielletanabe.Algorithms;

    uses com.github.elijahgabrielletanabe.AlgorithmBase;
    provides com.github.elijahgabrielletanabe.AlgorithmBase
        with com.github.elijahgabrielletanabe.Algorithms.MergeSort,
             com.github.elijahgabrielletanabe.Algorithms.SelectionSort,
             com.github.elijahgabrielletanabe.Algorithms.QuickSort,
             com.github.elijahgabrielletanabe.Algorithms.InsertionSort,
             com.github.elijahgabrielletanabe.Algorithms.BubbleSort;
}
