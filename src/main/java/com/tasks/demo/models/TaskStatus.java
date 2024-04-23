package com.tasks.demo.models;

import java.util.ArrayList;
import java.util.List;

public enum TaskStatus {
    NOT_DONE, IN_PROGRESS, DONE;

   static public List<String> getAll(){
        List<String> list = new ArrayList<String>();
        list.add(String.valueOf(NOT_DONE));
        list.add(String.valueOf(IN_PROGRESS));
        list.add(String.valueOf(DONE));
        return list;
    }

}
