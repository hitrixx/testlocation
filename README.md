Instructions:
to run application you need java, maven, and jdk

go to directory with app (testlocation)
<br/>
   <code>cd testlocation</code>
<br/>
then run <br/>
   <code>mvn spring-boot:run -Dspring-boot.run.arguments=--withTZ.vehicles.file=vehicles_with_tz.csv,--init.vehicles.file=vehicles.csv</code>
<br/>
where init.vehicles.file=vehicles.csv location of initial file
and withTZ.vehicles.file=vehicles_with_tz.csv is location of output file
