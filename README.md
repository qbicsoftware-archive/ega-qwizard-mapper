# ega-qwizard-mapper

### Usage

```java

String metaInfoFile = "/path/to/Sample_metainfo.map" ;
String analyzedInfoFile = "/path/to/Study_Sample_Analysis_File.map"

Mapper egaMapper = MapperFactory.getMapper(Files.lines(Paths.get(metaInfoFile)), Files.lines(Paths.get(analyzedInfoFile)));

System.out.println(egaMapper.getRawDataFile("AOCS-058-5-X");

```
