package org.xiao.message.common.page;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MongoPageImp<T>
{
    private final List<T> content = new ArrayList<T>();
    private int number;
    private int size;
    private int numberOfElements;
    private int totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
    public int getNumber()
    {
        return number==0?1:number;
    }
}