package org.aplatanao.billing.hibernate;

import org.hibernate.cfg.reveng.DefaultReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

public class ReverseEngineeringStrategy extends DefaultReverseEngineeringStrategy {

    @Override
    public String tableToClassName(TableIdentifier tableIdentifier) {
        String name = tableIdentifier.getName();
        if (name.length() > 2) {
            String suffix = "";
            switch (name.substring(0, 2)) {
                case "t_":
                    suffix = "Table";
                    break;
                case "v_":
                    suffix = "View";
                    break;
                case "r_":
                    suffix = "Resource";
                    break;
            }
            if (suffix.length() > 0) {
                name = name.substring(2) + suffix;
            }
        }
        return super.tableToClassName(new TableIdentifier(name));
    }
}