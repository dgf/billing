package org.aplatanao.billing.hibernate;

import static org.junit.Assert.assertEquals;

import org.hibernate.cfg.reveng.TableIdentifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RunWith(Parameterized.class)
public class ReverseEngineeringStrategyTest {

    private String tableName;
    private String className;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"a", "A"},
                {"ab", "Ab"},
                {"abc", "Abc"},
                {"ab_c", "AbC"},
                {"a_b", "AB"},
                {"a_type", "AType"},
                {"t_a", "Atable"},
                {"t_ab", "AbTable"},
                {"t_ab_cd", "AbCdTable"},
                {"v_b", "Bview"},
                {"v_bc", "BcView"},
                {"r_c", "Cresource"},
                {"r_cd", "CdResource"},
                {"t_invoice", "InvoiceTable"},
                {"v_invoices", "InvoicesView"},
                {"r_statistic", "StatisticResource"}});
    }

    public ReverseEngineeringStrategyTest(String tableName, String className) {
        this.tableName = tableName;
        this.className = className;
    }

    @Test
    public void tableToClassName() {
        assertEquals(className, new ReverseEngineeringStrategy().tableToClassName(new TableIdentifier(tableName)));
    }

}
