# Drugbank Converter to RDF

## About
A SpringBoot REST API service for converting DrugBank to RDF triples and uploading them to GraphDB. 

---
# Technologies
- Apache Maven
- SpringBoot 
- RDF4J Framework

---
# Run
Make sure you have `docker` and `docker-compose` installed before following the instructions.
The project is using an .env file to read environment variables so make sure to configure it before getting started. Before executing following command, fill the variable values in `.env.example` file.  
```sh
# Run the following command
cp .env.example .env
```
In order to serve the application run the following commands.
```sh
docker-compose build
docker-compose up
```