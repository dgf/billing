package org.aplatanao.billing.hibernate;

import org.hibernate.cfg.reveng.DefaultReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

public class ReverseEngineeringStrategy extends DefaultReverseEngineeringStrategy {

    @Override
    public String tableToClassName(TableIdentifier tableIdentifier) {
        System.out.println("tableToClassName");
        System.out.println("catalog " + tableIdentifier.getCatalog());
        System.out.println("name " + tableIdentifier.getName());
        System.out.println("schema " + tableIdentifier.getSchema());
        return super.tableToClassName(tableIdentifier) + "Table";
    }
}