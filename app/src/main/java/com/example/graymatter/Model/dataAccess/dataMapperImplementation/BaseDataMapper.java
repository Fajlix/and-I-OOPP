package com.example.graymatter.Model.dataAccess.dataMapperImplementation;

public class BaseDataMapper<T>/* implements DataMapper<T> */{
/*
    String dbPath;
    Gson gson = new Gson();

    protected BaseDataMapper(String dbPath){
        this.dbPath = dbPath;
    }

    @Override
    public Optional<T> find(int ID) {
        try {
            //TODO check when youre not this tired
            List<T> field = (List<T>) getDB().get(getClass());
            for (T o : field){
                if (o.toString().equals(String.valueOf(ID))){
                    return Optional.of(o);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void delete(T obj) throws DataMapperException {
        try {
            T toD = null;
            List<T> field = (List<T>) getDB().get(getClass());
            for (T o : field){
                if (o.toString().equals(obj.toString())){
                    toD = o;
                }
            }
            if (!field.remove(toD)){
                throw new DataMapperException("Object obj not present");
            }
            enterData(field);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(T obj) throws DataMapperException {
        List<T> field = (List<T>) getDB().get(getClass());
        for (T o : field){
            if (o.toString().equals(obj.toString())){
                field.remove(o);
                field.add(obj);
                try {
                    enterData(field);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void insert(T obj) {
        List<T> field = (List<T>) getDB().get(getClass());
        if (find(obj.toString()).isPresent()){
            throw new DataMapperException("Object already exists in database");
        }
        field.add(obj);
        try {
            enterData(field);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<T> get() {
        List<T> list = null;
        try {
            list = (List<T>) getDB().get(getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private DataBaseModel getDB() throws IOException {
        Reader reader = new FileReader(dbPath);
        DataBaseModel dbM = gson.fromJson(reader, DataBaseModel.class);
        reader.close();
        return dbM;
    }

    private void enterData(List<T> field) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Reader reader = new FileReader(dbPath);
        DataBaseModel nDb = gson.fromJson(reader, DataBaseModel.class);
        reader.close();
        nDb.set(field);
        Writer writer = new FileWriter(dbPath);
        gson.toJson(nDb, writer);
        writer.flush();
        writer.close();
    }
*/
}
