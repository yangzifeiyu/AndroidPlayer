package com.example.mfusion.model;

import java.util.ArrayList;

/**
 * Created by JCYYYY on 2016/5/26.
 */
public class MyTemplate {
    private ArrayList<TemplateComponent> list;
    private static int pointer=-1;
    private String name;
    private Integer id;

    public MyTemplate(TemplateComponent ...args){
        list=new ArrayList<>();
        for (int i=0;i<args.length;i++)
            list.add(args[i]);
    }

    public MyTemplate() {
        list=new ArrayList<>();
    }

    public void addComponent(TemplateComponent component){
        list.add(component);
    }

    public void clearComponent(){
        list.clear();
    }

    public ArrayList<TemplateComponent> getList() {
        return list;
    }

    public String getName(){return name;}

    public void setName(String name){this.name=name;}
    public void setList(ArrayList<TemplateComponent> list){this.list=list;}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id){this.id=id;}
}
