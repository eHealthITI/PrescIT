package com.example.Drugbank;

import com.example.drugbankspringboot.DrugbankHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/drugbank")
public class DrugbankConnector {
    private int i;
    private JSONParser jsonParser;
    private Object obj;
    private JSONObject jsonObj;
    private JSONArray dataArray;
    private ArrayList<Drug> drugs;
    @PostMapping("/synonyms/upload") // DONE
    public String synonymsUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            // initialize a jason parser
            jsonParser = new JSONParser();
            // create object that parses all json data
            obj = jsonParser.parse(body);
            // cast obj as JSONObject
            jsonObj = (JSONObject) obj;
            // insert data in JSONArray
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();

            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                System.out.println(dataRow.get("drugbank-id"));
                JSONObject synList = (JSONObject) dataRow.get("synonyms");
                // synonym list [{}. {}, .. ,{}]
                Object intervention = synList.get("synonym");
                ArrayList<Synonym> synonymArrayList = new ArrayList<>();
                if (intervention instanceof JSONArray) {
                    // It's an array
                    JSONArray syn = (JSONArray) synList.get("synonym");
                    for (Object synName : syn){
                        JSONObject synRow = (JSONObject) synName;
                        String synText = (String) synRow.get("#text");
                        String synCoder = (String) synRow.get("@coder");
                        String synLanguage = (String) synRow.get("@language");
                        Synonym mySyn = new Synonym(synLanguage, synCoder, synText);
                        synonymArrayList.add(mySyn);
                        System.out.println(synText);
                    }
                }
                else if (intervention instanceof JSONObject) {
                    // It's an object
                    JSONObject syn = (JSONObject) synList.get("synonym");
                    String synText = (String) syn.get("#text");
                    String synCoder = (String) syn.get("@coder");
                    String synLanguage = (String) syn.get("@language");
                    Synonym mySyn = new Synonym(synLanguage, synCoder, synText);
                    synonymArrayList.add(mySyn);
                    System.out.println(synText);
                }


                Drug myDrug = new Drug(dbID);
                myDrug.setSynonymArrayList(synonymArrayList);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                for(Synonym drugSyn : drug.getSynonymArrayList()){
                    System.out.println(drugSyn.getSynonymName() + ", " + drugSyn.getCoder() + ", " + drugSyn.getLanguage());
                }
            }

            DrugbankHandler.populateSynonyms(drugs);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/mechanismOfAction/upload") // DONE
    public String MOAUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String moa = (String) dataRow.get("mechanism-of-action");
                Drug myDrug = new Drug(dbID);
                myDrug.setMechanismOfAction(moa);
                drugs.add(myDrug);
            }
            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getMechanismOfAction());
            }
            DrugbankHandler.populateStringField(drugs, "moa");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/names/upload") // DONE
    public String namesUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String name = (String) dataRow.get("name");
                Drug myDrug = new Drug(dbID);
                myDrug.setName(name);
                drugs.add(myDrug);
            }
            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getName());
            }

            DrugbankHandler.populateStringField(drugs, "names");

        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/ddInteraction/upload") // DONE
    public String ddiUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
//                System.out.println(dbID);
                JSONObject ddiList = (JSONObject) dataRow.get("drug-interactions");
                // ddi list [{}. {}, .. ,{}]
                Object intervention = ddiList.get("drug-interaction");
                ArrayList<DDInteraction> ddInteractionArrayList = new ArrayList<>();
                if (intervention instanceof JSONArray) {
                    JSONArray ddi = (JSONArray) ddiList.get("drug-interaction");
                    for (Object ddiItem : ddi) {
                        JSONObject ddiRow = (JSONObject) ddiItem;
                        String ddiID = (String) ddiRow.get("drugbank-id");
                        String ddiName = (String) ddiRow.get("name");
                        String ddiDescription = (String) ddiRow.get("description");
                        DDInteraction myDDI = new DDInteraction(ddiName, ddiID, ddiDescription);
                        ddInteractionArrayList.add(myDDI);
//                        System.out.println(ddiID + ", " + ddiName + ", " + ddiDescription);
                    }
                }else if (intervention instanceof JSONObject) {
                    JSONObject ddi = (JSONObject) ddiList.get("drug-interaction");
                    String ddiID = (String) ddi.get("drugbank-id");
                    String ddiName = (String) ddi.get("name");
                    String ddiDescription = (String) ddi.get("description");
                    DDInteraction myDDI = new DDInteraction(ddiName, ddiID, ddiDescription);
                    ddInteractionArrayList.add(myDDI);
//                    System.out.println(ddiID + ", " + ddiName + ", " + ddiDescription);

                }
                Drug myDrug = new Drug(dbID);
                myDrug.setDdInteractionArrayList(ddInteractionArrayList);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                for(DDInteraction drugInter : drug.getDdInteractionArrayList()){
                    System.out.println(drugInter.getDrugbankID() + ", " + drugInter.getName() + ", " + drugInter.getDescription());
                }
            }

            DrugbankHandler.populateDDInteractions(drugs);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/pharmacodynamics/upload") // DONE
    public String pharmacodynamicsUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String pharmacodynamics = (String) dataRow.get("pharmacodynamics");

                Drug myDrug = new Drug(dbID);
                myDrug.setPharmacodynamics(pharmacodynamics);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getPharmacodynamics());
            }
            DrugbankHandler.populateStringField(drugs, "pharmacodynamics");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/indications/upload") // DONE
    public String indicationsUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String indication = (String) dataRow.get("indication");

                Drug myDrug = new Drug(dbID);
                myDrug.setIndication(indication);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getIndication());
            }
            DrugbankHandler.populateStringField(drugs, "indication");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }


    @PostMapping("/toxicity/upload") // DONE
    public String toxicityUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String toxicity = (String) dataRow.get("toxicity");

                Drug myDrug = new Drug(dbID);
                myDrug.setToxicity(toxicity);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getToxicity());
            }
            DrugbankHandler.populateStringField(drugs, "toxicity");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/state/upload") // DONE
    public String stateUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String state = (String) dataRow.get("state");

                Drug myDrug = new Drug(dbID);
                myDrug.setState(state);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getState());
            }
            DrugbankHandler.populateStringField(drugs, "state");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/categories/upload") // DONE
    public String categoriesUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
//                System.out.println(dbID);
                JSONObject catList = (JSONObject) dataRow.get("categories");
                Object intervention = catList.get("category");
                ArrayList<Category> categoryArrayList = new ArrayList<>();
                if (intervention instanceof JSONArray) {
                    JSONArray cat = (JSONArray) catList.get("category");
                    for (Object catItem : cat) {
                        JSONObject catRow = (JSONObject) catItem;
                        String catName = (String) catRow.get("category");
                        String meshID = (String) catRow.get("mesh-id");
                        Category myCat = new Category(catName, meshID);
                        categoryArrayList.add(myCat);
                    }
                }else if (intervention instanceof JSONObject) {
                    JSONObject cat = (JSONObject) catList.get("category");
                    String catName = (String) cat.get("category");
                    String meshID = (String) cat.get("mesh-id");
                    Category myCat = new Category(catName, meshID);
                    categoryArrayList.add(myCat);

                }
                Drug myDrug = new Drug(dbID);
                myDrug.setCategoryArrayList(categoryArrayList);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                for(Category category : drug.getCategoryArrayList()){
                    System.out.println(category.getCategoryName() + ", " + category.getMeshID());
                }
            }

            DrugbankHandler.populateCategories(drugs);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/description/upload") // DONE
    public String descriptionUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String description = (String) dataRow.get("description");

                Drug myDrug = new Drug(dbID);
                myDrug.setDescription(description);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getDescription());
            }
            DrugbankHandler.populateStringField(drugs, "description");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/unii/upload") // DONE
    public String uniiUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String unii = (String) dataRow.get("unii");

                Drug myDrug = new Drug(dbID);
                myDrug.setUNII(unii);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getUNII());
            }
            DrugbankHandler.populateStringField(drugs, "unii");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/casNumber/upload") // DONE
    public String casNumberUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String casNumber = (String) dataRow.get("cas-number");

                Drug myDrug = new Drug(dbID);
                myDrug.setCASNumber(casNumber);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getCASNumber());
            }
            DrugbankHandler.populateStringField(drugs, "cas");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/groups/upload") // DONE
    public String groupsUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                System.out.println(dbID);
                JSONObject groupList = (JSONObject) dataRow.get("groups");
                // ddi list [{}. {}, .. ,{}]
                Object intervention = groupList.get("group");
                ArrayList<String> groupArrayList = new ArrayList<>();
                System.out.println(intervention.getClass());
                if (intervention instanceof JSONArray) {
                    JSONArray group = (JSONArray) groupList.get("group");
                    for (Object groupItem : group) {
                        String groupID = (String) groupItem;
                        groupArrayList.add(groupID);
                    }
                }else if (intervention instanceof String) {
                    groupArrayList.add((String) intervention);
                }
                Drug myDrug = new Drug(dbID);
                myDrug.setGroupArrayList(groupArrayList);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                for(String group : drug.getGroupArrayList()){
                    System.out.println(group);
                }
            }

            DrugbankHandler.populateGroups(drugs);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/atc/upload") // DONE
    public String atcUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                JSONObject atcCodes = (JSONObject) dataRow.get("atc-codes");
                // ddi list [{}. {}, .. ,{}]
                Object intervention = atcCodes.get("atc-code");
                ArrayList<String> atcList = new ArrayList<>();
                if(intervention instanceof JSONObject jsonobj){
                    String atcItem = (String) jsonobj.get("Ccode");
                    atcList.add(atcItem);
                }
                else if (intervention instanceof JSONArray) {
                    for(Object atcObject : (JSONArray) intervention){
                        if(atcObject instanceof JSONObject jsonobj){
                            String atcItem = (String) jsonobj.get("Ccode");
                            atcList.add(atcItem);
                        }
                    }
                }

                Drug myDrug = new Drug(dbID);
                myDrug.setAtcCode(atcList);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                for(String atcItem : drug.getAtcCode())
                    System.out.println(atcItem);
                System.out.println();
            }

            DrugbankHandler.populateATC(drugs);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }
    @PostMapping("/product/upload") // DONE
    public String productUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                JSONObject products = (JSONObject) dataRow.get("products");
                // ddi list [{}. {}, .. ,{}]
                Object intervention = products.get("product");
                ArrayList<Product> productList = new ArrayList<>();
                if(intervention instanceof JSONObject jsonobj){
                    String productItem = (String) jsonobj.get("name");
                    String field1 = (String) jsonobj.get("labeller");
                    String field2 = (String) jsonobj.get("started-marketing-on");
                    String field3 = (String) jsonobj.get("ended-marketing-on");
                    String field4 = (String) jsonobj.get("dosage-form");
                    String field5 = (String) jsonobj.get("strength");
                    String field6 = (String) jsonobj.get("route");
                    String field7 = (String) jsonobj.get("fda-application-number");
                    String field8 = (String) jsonobj.get("approved");
                    String field9 = (String) jsonobj.get("country");
                    String field10 = (String) jsonobj.get("source");
                    Product product = new Product(productItem);
                    product.setLabeller(field1);
                    product.setStartedOn(field2);
                    product.setEndedOn(field3);
                    product.setDosageForm(field4);
                    product.setStrength(field5);
                    product.setRoute(field6);
                    product.setFdaAppNum(field7);
                    product.setApproved(field8);
                    product.setCountry(field9);
                    product.setSource(field10);
                    productList.add(product);
                }
                else if (intervention instanceof JSONArray) {
                    for(Object productObject : (JSONArray) intervention){
                        if(productObject instanceof JSONObject jsonobj){
                            String productItem = (String) jsonobj.get("name");
                            String field1 = (String) jsonobj.get("labeller");
                            String field2 = (String) jsonobj.get("started-marketing-on");
                            String field3 = (String) jsonobj.get("ended-marketing-on");
                            String field4 = (String) jsonobj.get("dosage-form");
                            String field5 = (String) jsonobj.get("strength");
                            String field6 = (String) jsonobj.get("route");
                            String field7 = (String) jsonobj.get("fda-application-number");
                            String field8 = (String) jsonobj.get("approved");
                            String field9 = (String) jsonobj.get("country");
                            String field10 = (String) jsonobj.get("source");
                            Product product = new Product(productItem);
                            product.setLabeller(field1);
                            product.setStartedOn(field2);
                            product.setEndedOn(field3);
                            product.setDosageForm(field4);
                            product.setStrength(field5);
                            product.setRoute(field6);
                            product.setFdaAppNum(field7);
                            product.setApproved(field8);
                            product.setCountry(field9);
                            product.setSource(field10);
                            productList.add(product);
                        }
                    }
                }


                Drug myDrug = new Drug(dbID);
                myDrug.setProductArrayList(productList);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                for(Product productItem : drug.getProductArrayList())
                    System.out.println(productItem.getName());
                System.out.println();
            }

            DrugbankHandler.populateProducts(drugs);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/half-life/upload") // DONE
    public String halfLifeUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String field = (String) dataRow.get("half-life");

                Drug myDrug = new Drug(dbID);
                myDrug.setHalfLife(field);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getHalfLife());
            }
            DrugbankHandler.populateStringField(drugs, "half-life");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/adverse-effect/upload") // DONE
    public String adverseEffectUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                JSONObject objList = (JSONObject) dataRow.get("snp-effects");
                Object intervention = objList.get("effect");
                ArrayList<AdverseEffect> fieldArrayList = new ArrayList<>();
                if (intervention instanceof JSONArray) {
                    JSONArray cat = (JSONArray) objList.get("effect");
                    for (Object catItem : cat) {
                        JSONObject catRow = (JSONObject) catItem;
                        String field1 = (String) catRow.get("protein-name");
                        String field2 = (String) catRow.get("gene-symbol");
                        String field3 = (String) catRow.get("uniprot-id");
                        String field4 = (String) catRow.get("rs-id");
                        String field5 = (String) catRow.get("description");
                        String field6 = (String) catRow.get("pubmed-id");
                        AdverseEffect myCat = new AdverseEffect(field5);
                        myCat.setGeneSymbol(field2);
                        myCat.setProteinName(field1);
                        myCat.setUniportID(field3);
                        myCat.setRsID(field4);
                        myCat.setPubmedID(field6);
                        fieldArrayList.add(myCat);
                    }
                }else if (intervention instanceof JSONObject) {
                    JSONObject cat = (JSONObject) objList.get("effect");
                    String field1 = (String) cat.get("protein-name");
                    String field2 = (String) cat.get("gene-symbol");
                    String field3 = (String) cat.get("uniprot-id");
                    String field4 = (String) cat.get("rs-id");
                    String field5 = (String) cat.get("description");
                    String field6 = (String) cat.get("pubmed-id");
                    AdverseEffect myCat = new AdverseEffect(field5);
                    myCat.setGeneSymbol(field2);
                    myCat.setProteinName(field1);
                    myCat.setUniportID(field3);
                    myCat.setRsID(field4);
                    myCat.setPubmedID(field6);
                    fieldArrayList.add(myCat);

                }
                Drug myDrug = new Drug(dbID);
                myDrug.setAdverseEffectArrayList(fieldArrayList);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                for(AdverseEffect effect : drug.getAdverseEffectArrayList()){
                    System.out.println(effect.getProteinName() + ", " + effect.getDescription() +
                            ", " + effect.getGeneSymbol() + ", " + effect.getRsID() + ", " + effect.getPubmedID() +
                            ", " + effect.getUniportID());
                }
            }

            DrugbankHandler.populateAdverseEffects(drugs);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/avg-weight/upload") // DONE
    public String avgWeightUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String field = (String) dataRow.get("average-mass");

                Drug myDrug = new Drug(dbID);
                myDrug.setAverageWeight(field);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getAverageWeight());
            }
            DrugbankHandler.populateStringField(drugs, "avg-weight");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/food-interaction/upload") // DONE
    public String foodInteractionUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                JSONObject objList = (JSONObject) dataRow.get("food-interactions");
                Object intervention = objList.get("food-interaction");
                if (intervention instanceof JSONArray) {
                    JSONArray cat = (JSONArray) objList.get("food-interaction");
                    String foodInteractions = "";
                    for (Object catItem : cat) {
                        String foodInteraction = (String) catItem;
                        String temp = foodInteractions.concat(foodInteraction);
                        foodInteractions = temp.concat("\n");
                    }
                    Drug myDrug = new Drug(dbID);
                    myDrug.setFoodInteraction(foodInteractions);
                    drugs.add(myDrug);
                }
                else if (intervention instanceof String foodInteraction) {
                    Drug myDrug = new Drug(dbID);
                    myDrug.setFoodInteraction(foodInteraction);
                    drugs.add(myDrug);
                }

            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getFoodInteraction());
            }
            DrugbankHandler.populateStringField(drugs, "food-interaction");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/drug-type/upload") // DONE
    public String drugTypeUploadToGraphDB(@RequestBody String body) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                String field = (String) dataRow.get("@type");

                Drug myDrug = new Drug(dbID);
                myDrug.setDrugType(field);
                drugs.add(myDrug);
            }

            for(Drug drug : drugs){
                i++;
                System.out.println(drug.getDrugbankID());
                System.out.println(drug.getDrugType());
            }
            DrugbankHandler.populateStringField(drugs, "type");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }

    @PostMapping("/external-identifier/upload") // DONE
    public String externalIdentifierUploadToGraphDB(@RequestBody String body, @RequestParam(value="resource") String res) {
        i = 0;
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(body);
            jsonObj = (JSONObject) obj;
            dataArray = (JSONArray) jsonObj.get("data");
            drugs = new ArrayList<>();
            for (Object o : dataArray) {
                JSONObject dataRow = (JSONObject) o;
                String dbID = (String) dataRow.get("drugbank-id");
                JSONObject catList = (JSONObject) dataRow.get("external-identifiers");
                Object intervention = catList.get("external-identifier");
                if (intervention instanceof JSONArray) {
                    JSONArray cat = (JSONArray) catList.get("external-identifier");
                    for (Object catItem : cat) {
                        JSONObject catRow = (JSONObject) catItem;
                        String resource = (String) catRow.get("resource");
                        String identifier = (String) catRow.get("identifier");
                        if(Objects.equals(resource, res)){
                            Drug myDrug = new Drug(dbID);
                            if(res.equals("PubChem Compound"))
                                myDrug.setPubchemSubstance(identifier);
                            else if (res.equals("KEGG Drug"))
                                myDrug.setKeggDrug(identifier);
                            drugs.add(myDrug);
                        }
                    }
                }else if (intervention instanceof JSONObject) {
                    JSONObject cat = (JSONObject) catList.get("external-identifier");
                    String resource = (String) cat.get("resource");
                    String identifier = (String) cat.get("identifier");
                    if(Objects.equals(resource, res)){
                        Drug myDrug = new Drug(dbID);
                        if(res.equals("PubChem Compound"))
                            myDrug.setPubchemSubstance(identifier);
                        else if (res.equals("KEGG Drug"))
                            myDrug.setKeggDrug(identifier);
                        drugs.add(myDrug);
                    }
                }
            }

            for(Drug drug : drugs){
                i++;
                if(Objects.equals(res, "PubChem Compound")){
                    System.out.println(drug.getDrugbankID() + "_" + drug.getPubchemSubstance());
                }else if (Objects.equals(res, "KEGG Drug")) {
                    System.out.println(drug.getDrugbankID() + "_" + drug.getKeggDrug());
                }
            }

            DrugbankHandler.populateStringField(drugs, res);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("Uploaded %d ", i);
    }


}
