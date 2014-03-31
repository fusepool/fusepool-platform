PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX dcterms: <http://purl.org/dc/terms/>
PREFIX foaf: <http://xmlns.com/foaf/0.1/>
PREFIX skos: <http://www.w3.org/2004/02/skos/core#>
PREFIX dbo: <http://dbpedia.org/ontology/>
PREFIX dbp: <http://dbpedia.org/property/>
PREFIX dbr: <http://dbpedia.org/resource/>
PREFIX prov: <http://www.w3.org/ns/prov#>
PREFIX year: <http://reference.data.gov.uk/id/year/>
PREFIX void: <http://rdfs.org/ns/void#>
PREFIX prov: <http://www.w3.org/ns/prov#>

PREFIX xkos: <http://purl.org/linked-data/xkos#>
PREFIX pso: <http://www.patexpert.org/ontologies/pso.owl#>
PREFIX pulo: <http://www.patexpert.org/ontologies/pulo.owl#>
PREFIX sumo: <http://www.owl-ontologies.com/sumo.owl#>
PREFIX pmo: <http://www.patexpert.org/ontologies/pmo.owl#>
PREFIX schema: <http://schema.org/>
PREFIX bibo: <http://purl.org/ontology/bibo/>

PREFIX property: <http://fusepool.info/property/>
PREFIX class: <http://fusepool.info/class/>
PREFIX dataset: <http://fusepool.info/dataset/>

#TODO: Separate class assignment?

CONSTRUCT {
#General purpose
    ?primaryURI
        a class:Call ;
        dcterms:identifier ?identifier ;
        owl:sameAs ?uuidURI ;
        dcterms:isPartOf ?isPartOfURI ;
        dcterms:hasPart ?hasPartURI ;
        dcterms:title ?titleWithLang ;
        rdfs:label ?titleWithLang ;
        dcterms:description ?descriptionWithLang ;
        property:validFrom ?validFromWithDatatype ;
        property:validTo ?validToWithDatatype ;
        skos:note ?notesWithLang ;
        rdfs:comment ?commentsWithLang ;

        rdfs:seeAlso ?seeAlsoURI ;
        dcterms:source ?sourceURI ;

#Data specific
        property:budget ?topicBudget ;
        property:fundingScheme ?fundingScheme ;
    .

    ?uuidURI
        owl:sameAs ?primaryURI .

    ?isPartOfURI
        a class:Topic ;
        dcterms:identifier ?isPartOfIdentifier ;
        dcterms:isPartOf ?primaryURI ;
    .
}
FROM <../data/calls.csv>
WHERE {
    BIND (REPLACE(REPLACE(?id, "^ +| +$", ''), ' ', '-') AS ?identifier)
    BIND (URI(CONCAT('urn:uuid:', REPLACE(REPLACE(?uuid, "^ +| +$", ''), ' ', '-'))) AS ?uuidURI)
    BIND (URI(CONCAT('http://fusepool.info/doc/call/', ?identifier)) AS ?primaryURI)
    BIND (REPLACE(REPLACE(?isPartOfId, "^ +| +$", ''), ' ', '-') AS ?isPartOfIdentifier)
    BIND (REPLACE(REPLACE(?hasPartId, "^ +| +$", ''), ' ', '-') AS ?hasPartIdentifier)
    BIND (URI(CONCAT('http://fusepool.info/doc/topic/', ?hasPartIdentifier)) AS ?hasPartURI)

    BIND (STRLANG(?title, "en") AS ?titleWithLang)
    BIND (STRLANG(?description, "en") AS ?descriptionWithLang)

    BIND (STRDT(REPLACE(?validFrom, ' ', ''), xsd:date) AS ?validFromWithDatatype)
    BIND (STRDT(REPLACE(?validUntil, ' ', ''), xsd:date) AS ?validUntilWithDatatype)

    BIND (STRLANG(?notes, "en") AS ?notesWithLang)
    BIND (STRLANG(?comments, "en") AS ?commentsWithLang)

    BIND (URI(REPLACE(REPLACE(?seeAlso, "^ +| +$", ''), ' ', '-')) AS ?seeAlsoURI)
    BIND (URI(REPLACE(REPLACE(?source, "^ +| +$", ''), ' ', '-')) AS ?sourceURI)

    BIND (STRLANG(?FundingScheme, "en") AS ?fundingScheme)
    BIND (STRLANG(?ProjectBudget, "en") AS ?topicBudget)

}
OFFSET 1
