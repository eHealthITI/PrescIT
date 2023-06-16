package com.example.drugbankspringboot;

import com.example.Drugbank.Drug;
import com.example.graphdb.Connector;
import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.util.RDFCollections;
import org.eclipse.rdf4j.model.vocabulary.RDF;

import java.util.ArrayList;
import java.util.Objects;

import static org.eclipse.rdf4j.model.util.Values.*;

public class DrugbankHandler {
    private static final String ONTOLOGY_URI = "http://www.semanticweb.org/drugbank-ontology#";
    // Classes
    private static final String DRUG = ONTOLOGY_URI+ "Drug";
    private static final String SYNONYM = ONTOLOGY_URI+ "Synonym";
    private static final String DDINTERACTION = ONTOLOGY_URI+ "DDInteraction";
    private static final String CATEGORY = ONTOLOGY_URI+ "Category";
    private static final String GROUP = ONTOLOGY_URI+ "Group";
    private static final String PRODUCT = ONTOLOGY_URI+ "Product";
    private static final String ADVERSE_REACTION = ONTOLOGY_URI+ "AdverseReaction";
    private static final String ARTICLE = ONTOLOGY_URI+ "Article";
    private static final String ATTACHMENT = ONTOLOGY_URI+ "Attachment";
    private static final String LINK = ONTOLOGY_URI+ "Link";
    private static final String TEXTBOOK = ONTOLOGY_URI+ "Textbook";

    // Object Properties
    private static final String _DDINTERACTION = ONTOLOGY_URI+ "ddinteraction";
    private static final String _SYNONYM = ONTOLOGY_URI+ "synonym";
    private static final String _CATEGORY = ONTOLOGY_URI+ "category";
    private static final String _GROUP = ONTOLOGY_URI+ "group";
    private static final String _PRODUCT = ONTOLOGY_URI+ "product";
    private static final String _ADVERSE_REACTION = ONTOLOGY_URI+ "adverseReaction";
    private static final String _HAS_ARTICLE = ONTOLOGY_URI+ "hasArticle";
    private static final String _HAS_ATTACHMENT = ONTOLOGY_URI+ "hasAttachment";
    private static final String _HAS_LINK = ONTOLOGY_URI+ "hasLink";
    private static final String _HAS_TEXTBOOK = ONTOLOGY_URI+ "hasTextbook";

    // Data type Properties
    // identification properties
    private static final String DRUGBANK_ID = ONTOLOGY_URI+ "drugbankID";
    private static final String DRUG_NAME = ONTOLOGY_URI+ "drugName";
    private static final String SYNONYM_LANGUAGE = ONTOLOGY_URI+ "synonymLanguage";
    private static final String SYNONYM_NAME = ONTOLOGY_URI+ "synonymName";
    private static final String STATE = ONTOLOGY_URI+ "state";
    private static final String DESCRIPTION = ONTOLOGY_URI+ "description";
    private static final String AVG_WEIGHT = ONTOLOGY_URI+ "avg_weight";
    private static final String DRUG_TYPE = ONTOLOGY_URI+ "type";

    // category properties
    private static final String CAT_NAME = ONTOLOGY_URI+ "categoryName";
    private static final String MESH_ID = ONTOLOGY_URI+ "meshID";
    private static final String ATC_CODE = ONTOLOGY_URI+ "atc-code";
    // interaction properties
    private static final String DDI_NAME = ONTOLOGY_URI+ "ddiName";
    private static final String DDI_DrugbankID = ONTOLOGY_URI+ "ddiDrugbankID";
    private static final String DDI_Description = ONTOLOGY_URI+ "ddiDescription";
    private static final String FOOD_INTERACTION = ONTOLOGY_URI+ "foodInteraction";
    // chemical id properties
    private static final String UNII = ONTOLOGY_URI+ "unii";
    private static final String CAS_NUMBER = ONTOLOGY_URI+ "casNumber";
    // external id properties
    private static final String KEGG = ONTOLOGY_URI+ "kegg";
    private static final String PUBCHEM_SUBSTANCE = ONTOLOGY_URI+ "pubChemSubstance";
    private static final String PUBCHEM_COMPOUND = ONTOLOGY_URI+ "pubChemCompound";

    // pharmacology properties
    private static final String PHARMACODYNAMICS = ONTOLOGY_URI+ "pharmacodynamics";
    private static final String INDICATION = ONTOLOGY_URI+ "indication";
    private static final String MOA = ONTOLOGY_URI+ "mechanismOfAction";
    private static final String TOXICITY = ONTOLOGY_URI+ "toxicity";
    private static final String HALF_LIFE = ONTOLOGY_URI+ "halfLife";
    private static final String ADV_REACT_DESCR = ONTOLOGY_URI+ "adverseReactionDescription";
    private static final String ADV_REACT_NAME = ONTOLOGY_URI+ "adverseReactionName";
    private static final String GENE_SYMBOL = ONTOLOGY_URI+ "gene_symbol";
    private static final String PROTEIN_NAME = ONTOLOGY_URI+ "protein_name";
    private static final String PUBMED_ID = ONTOLOGY_URI+ "pubmed_id";
    private static final String RS_ID = ONTOLOGY_URI+ "rs_id";
    private static final String UNIPORT_ID = ONTOLOGY_URI+ "uniport_id";
    // product properties
    private static final String PRODUCT_NAME = ONTOLOGY_URI+ "productName";
    private static final String PROD_APPROVED = ONTOLOGY_URI+ "approved";
    private static final String COUNTRY = ONTOLOGY_URI+ "country";
    private static final String DOSAGE_FORM = ONTOLOGY_URI+ "dosageForm";
    private static final String ENDED_ON = ONTOLOGY_URI+ "endedOn";
    private static final String FDA_APP_NUM = ONTOLOGY_URI+ "fda-application-number";
    private static final String LABELLER = ONTOLOGY_URI+ "labeller";
    private static final String ROUTE = ONTOLOGY_URI+ "route";
    private static final String STARTED_ON = ONTOLOGY_URI+ "startedOn";
    private static final String SOURCE = ONTOLOGY_URI+ "source";
    private static final String STRENGTH = ONTOLOGY_URI+ "strength";

    private static final String INT_BRAND_NAME = ONTOLOGY_URI+ "internationalBrandName";
    // reference properties
    private static final String CITATION = ONTOLOGY_URI+ "citation";
    private static final String ISBN = ONTOLOGY_URI+ "isbn";
    private static final String REF_ID = ONTOLOGY_URI+ "ref_id";
    private static final String REF_PUBMED_ID = ONTOLOGY_URI+ "ref_pubmed_id";
    private static final String REF_TITLE = ONTOLOGY_URI+ "ref_title";
    private static final String REF_URL = ONTOLOGY_URI+ "ref_url";

    // Group Instances
    private static final String APPROVED = ONTOLOGY_URI+ "Approved";
    private static final String EXPERIMENTAL = ONTOLOGY_URI+ "Experimental";
    private static final String ILLICIT = ONTOLOGY_URI+ "Illicit";
    private static final String INVESTIGATIONAL = ONTOLOGY_URI+ "Investigational";
    private static final String NUTRACEUTICAL = ONTOLOGY_URI+ "Nutraceutical";
    private static final String VET_APPROVED = ONTOLOGY_URI+ "Vet_approved";
    private static final String WITHDRAWN = ONTOLOGY_URI+ "Withdrawn";

    public static void populateSynonyms(ArrayList<Drug> drugs){
        Model model = new TreeModel();
        ValueFactory factory = SimpleValueFactory.getInstance();
        for(Drug drug : drugs){
            IRI drugItem = iri(DRUG+"_"+drug.getDrugbankID());
            model.add(drugItem,RDF.TYPE, iri(DRUG));
            model.add(drugItem,iri(DRUGBANK_ID), factory.createLiteral(drug.getDrugbankID()));
            for(int i=0; i<drug.getSynonymArrayList().size();i++){
                IRI synonym = iri(SYNONYM+"_"+drug.getDrugbankID()+"_"+i);
                if (Objects.nonNull(drug.getSynonymArrayList().get(i).getLanguage()))
                    model.add(synonym,iri(SYNONYM_LANGUAGE), factory.createLiteral(drug.getSynonymArrayList().get(i).getLanguage()));
                model.add(synonym,iri(SYNONYM_NAME), factory.createLiteral(drug.getSynonymArrayList().get(i).getSynonymName()));
                model.add(drugItem,iri(_SYNONYM),synonym);
            }
        }
        Connector.getDBRepoConnection().begin();
        Connector.getDBRepoConnection().add(model);
        Connector.getDBRepoConnection().commit();
    } //DONE

    public static void populateDDInteractions(ArrayList<Drug> drugs){
        Model model = new TreeModel();
        ValueFactory factory = SimpleValueFactory.getInstance();
        for(Drug drug : drugs){
            IRI drugItem = iri(DRUG+"_"+drug.getDrugbankID());
            model.add(drugItem,RDF.TYPE, iri(DRUG));
            model.add(drugItem,iri(DRUGBANK_ID), factory.createLiteral(drug.getDrugbankID()));
            for(int i=0; i<drug.getDdInteractionArrayList().size();i++){
                IRI ddi = iri(DDINTERACTION+"_"+drug.getDrugbankID()+"_"+drug.getDdInteractionArrayList().get(i).getDrugbankID());
                model.add(ddi,iri(DDI_NAME), factory.createLiteral(drug.getDdInteractionArrayList().get(i).getName()));
                model.add(ddi,iri(DDI_DrugbankID), factory.createLiteral(drug.getDdInteractionArrayList().get(i).getDrugbankID()));
                model.add(ddi,iri(DDI_Description), factory.createLiteral(drug.getDdInteractionArrayList().get(i).getDescription()));
                model.add(drugItem,iri(_DDINTERACTION),ddi);
            }
        }
        Connector.getDBRepoConnection().begin();
        Connector.getDBRepoConnection().add(model);
        Connector.getDBRepoConnection().commit();
    } // DONE

    public static void populateAdverseEffects(ArrayList<Drug> drugs){
        Model model = new TreeModel();
        ValueFactory factory = SimpleValueFactory.getInstance();
        for(Drug drug : drugs){
            IRI drugItem = iri(DRUG+"_"+drug.getDrugbankID());
            model.add(drugItem,RDF.TYPE, iri(DRUG));
            model.add(drugItem,iri(DRUGBANK_ID), factory.createLiteral(drug.getDrugbankID()));
            for(int i=0; i<drug.getAdverseEffectArrayList().size();i++){
                IRI adverse_effect = iri(ADVERSE_REACTION+"_"+drug.getDrugbankID()+"_"+i);
                if (Objects.nonNull(drug.getAdverseEffectArrayList().get(i).getGeneSymbol()))
                    model.add(adverse_effect,iri(GENE_SYMBOL), factory.createLiteral(drug.getAdverseEffectArrayList().get(i).getGeneSymbol()));
                if (Objects.nonNull(drug.getAdverseEffectArrayList().get(i).getProteinName()))
                    model.add(adverse_effect,iri(PROTEIN_NAME), factory.createLiteral(drug.getAdverseEffectArrayList().get(i).getProteinName()));
                if (Objects.nonNull(drug.getAdverseEffectArrayList().get(i).getDescription()))
                    model.add(adverse_effect,iri(ADV_REACT_DESCR), factory.createLiteral(drug.getAdverseEffectArrayList().get(i).getDescription()));
                if (Objects.nonNull(drug.getAdverseEffectArrayList().get(i).getRsID()))
                    model.add(adverse_effect,iri(RS_ID), factory.createLiteral(drug.getAdverseEffectArrayList().get(i).getRsID()));
//                if (Objects.nonNull(drug.getAdverseEffectArrayList().get(i).getUniportID()))
//                    model.add(adverse_effect,iri(UNIPORT_ID), factory.createLiteral(drug.getAdverseEffectArrayList().get(i).getUniportID()));
                if (Objects.nonNull(drug.getAdverseEffectArrayList().get(i).getPubmedID()))
                    model.add(adverse_effect,iri(PUBMED_ID), factory.createLiteral(drug.getAdverseEffectArrayList().get(i).getPubmedID()));

                model.add(drugItem,iri(_ADVERSE_REACTION),adverse_effect);
            }
        }
        Connector.getDBRepoConnection().begin();
        Connector.getDBRepoConnection().add(model);
        Connector.getDBRepoConnection().commit();
    } // DONE

    public static void populateCategories(ArrayList<Drug> drugs){
        Model model = new TreeModel();
        ValueFactory factory = SimpleValueFactory.getInstance();
        for(Drug drug : drugs){
            IRI drugItem = iri(DRUG+"_"+drug.getDrugbankID());
            model.add(drugItem,RDF.TYPE, iri(DRUG));
            model.add(drugItem,iri(DRUGBANK_ID), factory.createLiteral(drug.getDrugbankID()));
            for(int i=0; i<drug.getCategoryArrayList().size();i++){
                if (Objects.nonNull(drug.getCategoryArrayList().get(i).getMeshID())){
                    IRI cat = iri(CATEGORY+"_"+drug.getCategoryArrayList().get(i).getMeshID());
                    model.add(cat,iri(CAT_NAME), factory.createLiteral(drug.getCategoryArrayList().get(i).getCategoryName()));
                    model.add(cat,iri(MESH_ID), factory.createLiteral(drug.getCategoryArrayList().get(i).getMeshID()));
                    model.add(drugItem,iri(_CATEGORY),cat);
                }else{
                    String shortName = drug.getCategoryArrayList().get(i).getCategoryName().replace(" ", "");
                    shortName = shortName.replaceAll("[^A-Za-z0-9]", "");
                    IRI cat = iri(CATEGORY+"_"+shortName);
                    model.add(cat,iri(CAT_NAME), factory.createLiteral(drug.getCategoryArrayList().get(i).getCategoryName()));
                    model.add(drugItem,iri(_CATEGORY),cat);
                }
            }
        }
        Connector.getDBRepoConnection().begin();
        Connector.getDBRepoConnection().add(model);
        Connector.getDBRepoConnection().commit();
    } // DONE

    public static void populateGroups(ArrayList<Drug> drugs){
        Model model = new TreeModel();
        ValueFactory factory = SimpleValueFactory.getInstance();
        for(Drug drug : drugs) {
            IRI drugItem = iri(DRUG + "_" + drug.getDrugbankID());
            model.add(drugItem, RDF.TYPE, iri(DRUG));
            model.add(drugItem, iri(DRUGBANK_ID), factory.createLiteral(drug.getDrugbankID()));
            for (int i = 0; i < drug.getGroupArrayList().size(); i++) {
                if(Objects.equals(drug.getGroupArrayList().get(i), "approved"))
                    model.add(drugItem, iri(_GROUP), iri(APPROVED));
                else if(Objects.equals(drug.getGroupArrayList().get(i), "experimental"))
                    model.add(drugItem, iri(_GROUP), iri(EXPERIMENTAL));
                else if(Objects.equals(drug.getGroupArrayList().get(i), "illicit"))
                    model.add(drugItem, iri(_GROUP), iri(ILLICIT));
                else if(Objects.equals(drug.getGroupArrayList().get(i), "investigational"))
                    model.add(drugItem, iri(_GROUP), iri(INVESTIGATIONAL));
                else if(Objects.equals(drug.getGroupArrayList().get(i), "nutraceutical"))
                    model.add(drugItem, iri(_GROUP), iri(NUTRACEUTICAL));
                else if(Objects.equals(drug.getGroupArrayList().get(i), "vet_approved"))
                    model.add(drugItem, iri(_GROUP), iri(VET_APPROVED));
                else if(Objects.equals(drug.getGroupArrayList().get(i), "withdrawn"))
                    model.add(drugItem, iri(_GROUP), iri(WITHDRAWN));
            }
        }
        Connector.getDBRepoConnection().begin();
        Connector.getDBRepoConnection().add(model);
        Connector.getDBRepoConnection().commit();

    } // DONE

    public static void populateProducts(ArrayList<Drug> drugs){
        Model model = new TreeModel();
        ValueFactory factory = SimpleValueFactory.getInstance();
        for(Drug drug : drugs) {
            IRI drugItem = iri(DRUG + "_" + drug.getDrugbankID());
            model.add(drugItem, RDF.TYPE, iri(DRUG));
            model.add(drugItem, iri(DRUGBANK_ID), factory.createLiteral(drug.getDrugbankID()));
            for (int i = 0; i < drug.getProductArrayList().size(); i ++){
                IRI product = iri(PRODUCT+"_"+drug.getDrugbankID()+"_"+i);
                model.add(product, RDF.TYPE, iri(PRODUCT));
                model.add(product,iri(PRODUCT_NAME), factory.createLiteral(drug.getProductArrayList().get(i).getName()));
                if (Objects.nonNull(drug.getProductArrayList().get(i).getLabeller()))
                    model.add(product,iri(LABELLER), factory.createLiteral(drug.getProductArrayList().get(i).getLabeller()));
                if (Objects.nonNull(drug.getProductArrayList().get(i).getStartedOn()))
                    model.add(product,iri(STARTED_ON), factory.createLiteral(drug.getProductArrayList().get(i).getStartedOn()));
                if (Objects.nonNull(drug.getProductArrayList().get(i).getEndedOn()))
                    model.add(product,iri(ENDED_ON), factory.createLiteral(drug.getProductArrayList().get(i).getEndedOn()));
                if (Objects.nonNull(drug.getProductArrayList().get(i).getDosageForm()))
                    model.add(product,iri(DOSAGE_FORM), factory.createLiteral(drug.getProductArrayList().get(i).getDosageForm()));
                if (Objects.nonNull(drug.getProductArrayList().get(i).getStrength()))
                    model.add(product,iri(STRENGTH), factory.createLiteral(drug.getProductArrayList().get(i).getStrength()));
                if (Objects.nonNull(drug.getProductArrayList().get(i).getRoute()))
                    model.add(product,iri(ROUTE), factory.createLiteral(drug.getProductArrayList().get(i).getRoute()));
                if (Objects.nonNull(drug.getProductArrayList().get(i).getFdaAppNum()))
                    model.add(product,iri(FDA_APP_NUM), factory.createLiteral(drug.getProductArrayList().get(i).getFdaAppNum()));
                if (Objects.nonNull(drug.getProductArrayList().get(i).getApproved()))
                    model.add(product,iri(PROD_APPROVED), factory.createLiteral(drug.getProductArrayList().get(i).getApproved()));
                if (Objects.nonNull(drug.getProductArrayList().get(i).getCountry()))
                    model.add(product,iri(COUNTRY), factory.createLiteral(drug.getProductArrayList().get(i).getCountry()));
                if (Objects.nonNull(drug.getProductArrayList().get(i).getSource()))
                    model.add(product,iri(SOURCE), factory.createLiteral(drug.getProductArrayList().get(i).getSource()));
                model.add(drugItem,iri(_PRODUCT),product);
            }
        }
        Connector.getDBRepoConnection().begin();
        Connector.getDBRepoConnection().add(model);
        Connector.getDBRepoConnection().commit();
    } // DONE

    public static void populateATC(ArrayList<Drug> drugs){
        ArrayList<Model> modelArrayList = new ArrayList<>();
        for (Drug drug : drugs){
            IRI drugItem = iri(DRUG + "_" + drug.getDrugbankID());
            Resource head = bnode();
            Model model = RDFCollections.asRDF(drug.getAtcCode(), head, new LinkedHashModel());
            model.add(drugItem, iri(ATC_CODE), head);
            modelArrayList.add(model);
        }
        Connector.getDBRepoConnection().begin();
        for(Model model : modelArrayList){
            Connector.getDBRepoConnection().add(model);
        }
        Connector.getDBRepoConnection().commit();
    } // DONE

    public static void populateStringField(ArrayList<Drug> drugs, String field){
        Model model = new TreeModel();
        ValueFactory factory = SimpleValueFactory.getInstance();
        for(Drug drug : drugs){
            IRI drugItem = iri(DRUG+"_"+drug.getDrugbankID());
            model.add(drugItem,RDF.TYPE, iri(DRUG));
            model.add(drugItem,iri(DRUGBANK_ID), factory.createLiteral(drug.getDrugbankID()));
            if (Objects.equals(field, "pharmacodynamics")){
                model.add(drugItem,iri(PHARMACODYNAMICS), factory.createLiteral(drug.getPharmacodynamics()));
            }else if (Objects.equals(field, "names")){
                model.add(drugItem,iri(DRUG_NAME), factory.createLiteral(drug.getName()));
            } else if (Objects.equals(field, "moa")) {
                model.add(drugItem,iri(MOA), factory.createLiteral(drug.getMechanismOfAction()));
            } else if (Objects.equals(field, "indication")){
                model.add(drugItem,iri(INDICATION), factory.createLiteral(drug.getIndication()));
            } else if (Objects.equals(field, "toxicity")){
                model.add(drugItem,iri(TOXICITY), factory.createLiteral(drug.getToxicity()));
            }else if (Objects.equals(field, "state")){
                model.add(drugItem,iri(STATE), factory.createLiteral(drug.getState()));
            }else if (Objects.equals(field, "description")){
                model.add(drugItem,iri(DESCRIPTION), factory.createLiteral(drug.getDescription()));
            }else if (Objects.equals(field, "unii")){
                model.add(drugItem,iri(UNII), factory.createLiteral(drug.getUNII()));
            }
            else if (Objects.equals(field, "cas")){
                model.add(drugItem,iri(CAS_NUMBER), factory.createLiteral(drug.getCASNumber()));
            }
            else if (Objects.equals(field, "half-life")){
                model.add(drugItem,iri(HALF_LIFE), factory.createLiteral(drug.getHalfLife()));
            }
            else if (Objects.equals(field, "avg-weight")){
                model.add(drugItem,iri(AVG_WEIGHT), factory.createLiteral(drug.getAverageWeight()));
            }
            else if (Objects.equals(field, "food-interaction")){
                model.add(drugItem,iri(FOOD_INTERACTION), factory.createLiteral(drug.getFoodInteraction()));
            }
            else if (Objects.equals(field, "type")){
                model.add(drugItem,iri(DRUG_TYPE), factory.createLiteral(drug.getDrugType()));
            }
            else if (Objects.equals(field, "PubChem Compound")){
                model.add(drugItem,iri(PUBCHEM_COMPOUND), factory.createLiteral(drug.getPubchemSubstance()));
            }
            else if (Objects.equals(field, "KEGG Drug")){
                model.add(drugItem,iri(KEGG), factory.createLiteral(drug.getKeggDrug()));
            }
        }
        Connector.getDBRepoConnection().begin();
        Connector.getDBRepoConnection().add(model);
        Connector.getDBRepoConnection().commit();
    }

}
