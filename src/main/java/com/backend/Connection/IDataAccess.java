package com.backend.Connection;

import java.util.ArrayList;

public interface IDataAccess <Template> {
    public int insert(Template template);

    public int update(Template template);

    public int delete(Template template);

    public ArrayList<Template> selectAll();

}
