# JAVA-ALGORITHM-SPEED-VISUALIZER

As the name suggests, this is an algorithm (specifically sorting) speed visualizer in Java. This project uses Apache Maven as the build tool and JavaFX as the GUI with the help of Gluon's SceneBuilder. This project does not require the JDK or Apache Maven to be run. <br/> 

Being new with Maven and JavaFX, this project came with **ALOT** of headaches (files paths, fx css properties, node customization, etc.) but it was still overall a fun and good learning experience that I would recommend to anyone getting into Java GUI applications.

## How to run

#### With Image
1) Download the image from the [image branch](https://github.com/ElijahGabrielleTanabe/java-algo-speed-visualizer/tree/image).
2) Unzip the file and locate the bin folder within the image.
3) Run the launch.bat file.
> This image was created with Jlink and contains a JRE, the necessary dependencies, and the source files. Feel free to verify its contents before you run it

#### With JDK and Maven
1) Download the full Maven project in the [main branch](https://github.com/ElijahGabrielleTanabe/java-algo-speed-visualizer/tree/main).
2) Open a terminal and cd into the project directory at the same level as the pom.xml file.
3) Run the command `mvn clean javafx:run`.

## Notes

- You will notice that running an algorithm the second time is faster than the first time, this is due to JIT compiler optimizations and is something I will fix ~~*(probably)*~~ so that it is more consistant.
- This application has not been throughly tested in terms of accurate data and resource usage which is something to keep in mind.
- This **absolutely** does not follow any software design pattern because I didn't have time to learn them and wanted mainly to focus on understanding how JavaFX works for future projects.

## To-do

- [ ] **Sort Size Input:** User specified input for the sort sizes (and maybe some presets)
- [ ] **Line Graph Colours:** User specified line graph colours for each algorithm
- [ ] **Min-Max Bounds:** User specified min and max bounds for the y-axis
- [ ] **UI Style Revision:** More colours basically

## Timeline

Research & Experimentation : May 7-10th <br/>
First Round Implementation : May 11-16th