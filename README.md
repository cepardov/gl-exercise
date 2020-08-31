## _GLExcercise_

`Java 8`
`Gradle`
`Spock`

#### _Tasks_
```
gradle clean
```
```
gradle build
```
```
gradle test
```
```
gradle bootRun
```

#### _Test the app_

* Consultar sismos entre 2 fechas. **POST**
```
http://localhost:9090/api/v1/earthquake/between-dates
{
  "startTime": "2020-08-01",
  "endTime": "2020-08-02"
}
```

* Consultar sismos entre 2 magnitudes. **POST**
```
http://localhost:9090/api/v1/earthquake/between-magnitude
{
  "maxMagnitude": 9.0,
  "minMagnitude": 7.0
}
```

* Consultar sismos entre 2 tramos de fechas. **POST**
```
http://localhost:9090/api/v1/earthquake/between-one-or-more-dates
[
  {
    "endTime": "2019-10-03",
    "startTime": "2019-10-06"
  },
  {
    "endTime": "2019-10-20",
    "startTime": "2019-10-14"
  }
]
```

* Consultar sismos por pais. **GET**
```
http://localhost:9090/api/v1/earthquake/getAllByPlace/{place} <- String

```

* Consultar sismos por pais entre 2 fechas. **POST**
```
http://localhost:9090/api/v1/earthquake/getQuakesByPlaceAndDates/{place} <- String
{
  "endTime": "2020-08-31",
  "startTime": "2020-08-28"
}
```

