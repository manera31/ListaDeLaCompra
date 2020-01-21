package com.joanmanera.listadelacompra.Interfaces;

import com.joanmanera.listadelacompra.Models.List;

import java.io.Serializable;
import java.util.ArrayList;

public interface IListListener extends Serializable {
    void onListSelected(int list);
}
