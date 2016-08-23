# GroovyDatasetGenerator
-----------------------------------------------------------------------

What?
=====
__Groovy Datasets Generator__ is framework that helps generating a lot of testing data in simple readable brief groovy script.  
It is based on Groovy domain-specific language (DSL) using MarkupBuilder to generate .xml datasets for DBUnit.

Why?
=====
Your test need some data to work with. This means you must create a dataset. In most situations you will work with xml datasets.  
XML datasets may be hand written or exported from database, then stored in project resources and uploaded to Database while tests running. That mean a lot of .xml files for each test case, meanwhile datasets itself differs not so much (copy-pasted). Also it's hard to support all these stored files if expansions needed.  

Groovy Datasets Generator provides the following advantages:
 * Using templates and overriding
 * Easy generating sequences
 * Clear data structure
 * Coding fast and simple
 * On-fly or saved result usage
 * Directly using java code in script.
 
How?
=====
While running groovy script Groovy Datasets Generator is looking for methods to be represented as xml via MarkupBuilder.  Please find script examples in `resources/datasets` folder.  
Models used as templates with default field's values are stored in basePackage `com.griddynamics.model.`

###IDE: 
Run - Application:  
Main class: `com.griddynamics.platform.util.dbunit.DbUnitGroovyScriptRunner`  
Program Arguments: `path_to_groovy_script` `[basePackage]`

###Using: 
```java 
com.griddynamics.platform.util.dbunit.DbUnitGroovyScriptRunner.generate(scriptInputStream, xmlWriter, basePackage);

