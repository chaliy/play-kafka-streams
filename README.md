## Run application

To run main pipeline
```sh
./gradlew run
```

To run fake producer to generate message every 20 seconds
```sh
./gradlew fakeProducer
```


## Entrypoints

Entrypoint | Description | Credentials
--- | --- | ---
http://localhost:8080 | Kafka UI | --


## KSQL

```sh
docker compose exec -it ksql-cli ksql http://ksqldb:8088
```

From https://developer.confluent.io/courses/ksqldb/hands-on-interacting-with-ksqldb/

```sql
CREATE STREAM MOVEMENTS (PERSON VARCHAR KEY, LOCATION VARCHAR) WITH (VALUE_FORMAT='JSON', PARTITIONS=1, KAFKA_TOPIC='movements');

INSERT INTO MOVEMENTS VALUES ('Allison', 'Denver');
INSERT INTO MOVEMENTS VALUES ('Robin', 'Leeds');
INSERT INTO MOVEMENTS VALUES ('Robin', 'Ilkley');
INSERT INTO MOVEMENTS VALUES ('Allison', 'Boulder');

SELECT * FROM MOVEMENTS EMIT CHANGES;


CREATE TABLE PERSON_STATS WITH (VALUE_FORMAT='AVRO') AS
  SELECT PERSON,
    LATEST_BY_OFFSET(LOCATION) AS LATEST_LOCATION,
    COUNT(*) AS LOCATION_CHANGES,
    COUNT_DISTINCT(LOCATION) AS UNIQUE_LOCATIONS
  FROM MOVEMENTS
GROUP BY PERSON
EMIT CHANGES;


show tables; 


SELECT * FROM PERSON_STATS;
```

## Resources

- [Confluent docker-compose.yml](https://github.com/confluentinc/demo-scene/blob/master/community-components-only/docker-compose.yml)