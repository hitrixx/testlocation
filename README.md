Instructions:
to run application you need java, maven, and jdk

go to directory with app (testlocation)
   <code>cd testlocation</code>
then run
   <code>mvn spring-boot:run -Dspring-boot.run.arguments=--withTZ.vehicles.file=vehicles_with_tz.csv,--init.vehicles.file=vehicles.csv</code>
where init.vehicles.file=vehicles.csv location of initial file
and withTZ.vehicles.file=vehicles_with_tz.csv is location of output file

archive in atachment