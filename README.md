# dependency-injector
intelliJ Plugin automatically injects the most popular version

<br>

## Short Cut ✂️  
<ul>
    <li> Window : <code>Alt + Shift + D</code> </li>
    <li> Mac : <code>Command + Shift + D</code> </li>
</ul>

<br> 

## How To Use 📔
Select the keyword you want to inject dependency, and press the shortcut. <br/>
the pluging then searches the <a href="https://mvnrepository.com">mvnrepository.com</a> for thre word and shows three choices in order of popularity. <br/>
If you select the dependency you want, find the most popular version and inject it automatically for each build tool. (maven, gradle)

<br>

![image](https://github.com/shinsunyoung/dependency-injector/blob/master/maven.gif?raw=true) <br>
pom.xml (Maven) 

<br>

![image](https://github.com/shinsunyoung/dependency-injector/blob/master/gradle.gif?raw=true) <br>
build.gradle (Gradle)

<br>

## Change Note
1.0 : New project initiate. <br>
1.1 : Change sort order from popularity to relevance
