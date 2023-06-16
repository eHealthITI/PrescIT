# Drugbank Knowledge Graph

![](./Drugbank_logo.png)


[DrugBank](https://go.drugbank.com/) is an free-to-access online database, with a free license for research purposes containing information on a variety of drugs and ADRs (Adverse Drug Reaction). 

In this repository you may find:
1. A zip file including all downloaded data from Drugbank website.
2. The Drugbank Ontology that we created to model Drugbank in Turtle format.
3. A zip file including the Drugbank Knowledge Graph that contains the ontology and the Drugbank dataset in RDF format.
4. The Java app that we developed in order to covert the Drugbank dataset to RDF triples. 

## Initial Data
The DrugBank dataset is originally available in an xml file which is accompanied by an XSD file that defines the XML Schema followed by the data. In addition, a csv file with all drug entitites ids is included.   

## Drugbank conversion to RDF format
In order to convert this dataset into RDF triples, we initially developed an ontology with respect to the given XML Schema. To achieve this, we followed some basic principles of converting an XSD format to OWL ontology. More specifically, the "ComplexType" objects of XML schema translated to OWL classes, the "SimpleType" and the "Attribute" objects translated to OWL predicates and the "Type" objects translated to OWL datatype properties. Then, we automatically converted the dataset into RDF triples via a Java script, using the [RDF4J](https://rdf4j.org/) library.




