/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LTorres
 */
public class TPCHQueries {

    Connection con = null;

    public TPCHQueries() {
    }

    public TPCHQueries(Connection con) {
        this.con = con;
    }

    public Connection getConnection() {
        return con;
    }

    public void setConnection(Connection con) {
        this.con = con;
    }
    
    public void runQueries(){
        query3();
        System.out.print("3, ");
        query5();
        System.out.print("5, ");
        query6();
        System.out.print("6, ");
        query7();
        System.out.print("7, ");
        query8();
        System.out.print("8, ");
        query9();
        System.out.print("9, ");
        query10();
        System.out.print("10, ");
        query11();
        System.out.print("11, ");
        query12();
        System.out.print("12, ");
        query13();
        System.out.print("13, ");
        query14();
        System.out.print("14, ");
        query16();
        System.out.print("16, ");
        query18();
        System.out.print("18, ");
        query19();
        System.out.print("19, ");
        query22();
        System.out.println("22.");
    }

    public void executeQuery(String sql) {

        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(TPCHQueries.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }

    }

    public void query1() {

        String sql;
        sql = "select\n"
                + "        l_returnflag,\n"
                + "        l_linestatus,\n"
                + "        sum(l_quantity) as sum_qty,\n"
                + "        sum(l_extendedprice) as sum_base_price,\n"
                + "        sum(l_extendedprice * (1 - l_discount)) as sum_disc_price,\n"
                + "        sum(l_extendedprice * (1 - l_discount) * (1 + l_tax)) as sum_charge,\n"
                + "        avg(l_quantity) as avg_qty,\n"
                + "        avg(l_extendedprice) as avg_price,\n"
                + "        avg(l_discount) as avg_disc,\n"
                + "        count(*) as count_order\n"
                + "from\n"
                + "        lineitem\n"
                + "where\n"
                + "        l_shipdate <= date '1998-12-01' - interval '90' day\n"
                + "group by\n"
                + "        l_returnflag,\n"
                + "        l_linestatus\n"
                + "order by\n"
                + "        l_returnflag,\n"
                + "        l_linestatus;";

        executeQuery(sql);
    }

    public void query2() {

        String sql;
        sql = "select\n"
                + "        s_acctbal,\n"
                + "        s_name,\n"
                + "        n_name,\n"
                + "        p_partkey,\n"
                + "        p_mfgr,\n"
                + "        s_address,\n"
                + "        s_phone,\n"
                + "        s_comment\n"
                + "from\n"
                + "        part,\n"
                + "        supplier,\n"
                + "        partsupp,\n"
                + "        nation,\n"
                + "        region\n"
                + "where\n"
                + "        p_partkey = ps_partkey\n"
                + "        and s_suppkey = ps_suppkey\n"
                + "        and p_size = 15\n"
                + "        and p_type like '%BRASS'\n"
                + "        and s_nationkey = n_nationkey\n"
                + "        and n_regionkey = r_regionkey\n"
                + "        and r_name = 'EUROPE'\n"
                + "        and ps_supplycost = (\n"
                + "                select\n"
                + "                        min(ps_supplycost)\n"
                + "                from\n"
                + "                        partsupp,\n"
                + "                        supplier,\n"
                + "                        nation,\n"
                + "                        region\n"
                + "                where\n"
                + "                        p_partkey = ps_partkey\n"
                + "                        and s_suppkey = ps_suppkey\n"
                + "                        and s_nationkey = n_nationkey\n"
                + "                        and n_regionkey = r_regionkey\n"
                + "                        and r_name = 'EUROPE'\n"
                + "        )\n"
                + "order by\n"
                + "        s_acctbal desc,\n"
                + "        n_name,\n"
                + "        s_name,\n"
                + "        p_partkey\n"
                + "limit 100;";

        executeQuery(sql);
    }

    public void query3() {

        String sql;
        sql = "select\n"
                + "        l_orderkey,\n"
                + "        sum(l_extendedprice * (1 - l_discount)) as revenue,\n"
                + "        o_orderdate,\n"
                + "        o_shippriority\n"
                + "from\n"
                + "        customer,\n"
                + "        orders,\n"
                + "        lineitem\n"
                + "where\n"
                + "        c_mktsegment = 'BUILDING'\n"
                + "        and c_custkey = o_custkey\n"
                + "        and l_orderkey = o_orderkey\n"
                + "        and o_orderdate < date '1995-03-15'\n"
                + "        and l_shipdate > date '1995-03-15'\n"
                + "group by\n"
                + "        l_orderkey,\n"
                + "        o_orderdate,\n"
                + "        o_shippriority\n"
                + "order by\n"
                + "        revenue desc,\n"
                + "        o_orderdate\n"
                + "limit 10;";

        executeQuery(sql);
    }

    public void query4() {
        String sql;
        sql = "select\n"
                + "        o_orderpriority,\n"
                + "        count(*) as order_count\n"
                + "from\n"
                + "        orders\n"
                + "where\n"
                + "        o_orderdate >= date '1993-07-01'\n"
                + "        and o_orderdate < date '1993-07-01' + interval '3' month\n"
                + "        and exists (\n"
                + "                select\n"
                + "                        *\n"
                + "                from\n"
                + "                        lineitem\n"
                + "                where\n"
                + "                        l_orderkey = o_orderkey\n"
                + "                        and l_commitdate < l_receiptdate\n"
                + "        )\n"
                + "group by\n"
                + "        o_orderpriority\n"
                + "order by\n"
                + "        o_orderpriority;";
        executeQuery(sql);
    }

    public void query5() {
        String sql;
        sql = "select\n"
                + "        n_name,\n"
                + "        sum(l_extendedprice * (1 - l_discount)) as revenue\n"
                + "from\n"
                + "        customer,\n"
                + "        orders,\n"
                + "        lineitem,\n"
                + "        supplier,\n"
                + "        nation,\n"
                + "        region\n"
                + "where\n"
                + "        c_custkey = o_custkey\n"
                + "        and l_orderkey = o_orderkey\n"
                + "        and l_suppkey = s_suppkey\n"
                + "        and c_nationkey = s_nationkey\n"
                + "        and s_nationkey = n_nationkey\n"
                + "        and n_regionkey = r_regionkey\n"
                + "        and r_name = 'ASIA'\n"
                + "        and o_orderdate >= date '1994-01-01'\n"
                + "        and o_orderdate < date '1994-01-01' + interval '1' year\n"
                + "group by\n"
                + "        n_name\n"
                + "order by\n"
                + "        revenue desc;";
        executeQuery(sql);
    }

    public void query6() {
        String sql;
        sql = "select\n"
                + "        sum(l_extendedprice * l_discount) as revenue\n"
                + "from\n"
                + "        lineitem\n"
                + "where\n"
                + "        l_shipdate >= date '1994-01-01'\n"
                + "        and l_shipdate < date '1994-01-01' + interval '1' year\n"
                + "        and l_discount between .06 - 0.01 and .06 + 0.01\n"
                + "        and l_quantity < 24;";
        executeQuery(sql);
    }

    public void query7() {
        String sql;
        sql = "select\n"
                + "        supp_nation,\n"
                + "        cust_nation,\n"
                + "        l_year,\n"
                + "        sum(volume) as revenue\n"
                + "from\n"
                + "        (\n"
                + "                select\n"
                + "                        n1.n_name as supp_nation,\n"
                + "                        n2.n_name as cust_nation,\n"
                + "                        extract(year from l_shipdate) as l_year,\n"
                + "                        l_extendedprice * (1 - l_discount) as volume\n"
                + "                from\n"
                + "                        supplier,\n"
                + "                        lineitem,\n"
                + "                        orders,\n"
                + "                        customer,\n"
                + "                        nation n1,\n"
                + "                        nation n2\n"
                + "                where\n"
                + "                        s_suppkey = l_suppkey\n"
                + "                        and o_orderkey = l_orderkey\n"
                + "                        and c_custkey = o_custkey\n"
                + "                        and s_nationkey = n1.n_nationkey\n"
                + "                        and c_nationkey = n2.n_nationkey\n"
                + "                        and (\n"
                + "                                (n1.n_name = 'FRANCE' and n2.n_name = 'GERMANY')\n"
                + "                                or (n1.n_name = 'GERMANY' and n2.n_name = 'FRANCE')\n"
                + "                        )\n"
                + "                        and l_shipdate between date '1995-01-01' and date '1996-12-31'\n"
                + "        ) as shipping\n"
                + "group by\n"
                + "        supp_nation,\n"
                + "        cust_nation,\n"
                + "        l_year\n"
                + "order by\n"
                + "        supp_nation,\n"
                + "        cust_nation,\n"
                + "        l_year;";
        executeQuery(sql);
    }

    public void query8() {
        String sql;
        sql = "select\n"
                + "        o_year,\n"
                + "        sum(case\n"
                + "                when nation = 'BRAZIL' then volume\n"
                + "                else 0\n"
                + "        end) / sum(volume) as mkt_share\n"
                + "from\n"
                + "        (\n"
                + "                select\n"
                + "                        extract(year from o_orderdate) as o_year,\n"
                + "                        cast(l_extendedprice as double precision) * cast((1 - l_discount) as double precision) as volume,\n"
                + "                        n2.n_name as nation\n"
                + "                from\n"
                + "                        part,\n"
                + "                        supplier,\n"
                + "                        lineitem,\n"
                + "                        orders,\n"
                + "                        customer,\n"
                + "                        nation n1,\n"
                + "                        nation n2,\n"
                + "                        region\n"
                + "                where\n"
                + "                        p_partkey = l_partkey\n"
                + "                        and s_suppkey = l_suppkey\n"
                + "                        and l_orderkey = o_orderkey\n"
                + "                        and o_custkey = c_custkey\n"
                + "                        and c_nationkey = n1.n_nationkey\n"
                + "                        and n1.n_regionkey = r_regionkey\n"
                + "                        and r_name = 'AMERICA'\n"
                + "                        and s_nationkey = n2.n_nationkey\n"
                + "                        and o_orderdate between date '1995-01-01' and date '1996-12-31'\n"
                + "                        and p_type = 'ECONOMY ANODIZED STEEL'\n"
                + "        ) as all_nations\n"
                + "group by\n"
                + "        o_year\n"
                + "order by\n"
                + "        o_year;";
        executeQuery(sql);
    }

    public void query9() {
        String sql;
        sql = "select\n"
                + "        nation,\n"
                + "        o_year,\n"
                + "        sum(amount) as sum_profit\n"
                + "from\n"
                + "        (select\n"
                + "                        n_name as nation,\n"
                + "                        extract(year from o_orderdate) as o_year,\n"
                + "                        l_extendedprice * (1 - l_discount) - ps_supplycost * l_quantity as amount\n"
                + "                from\n"
                + "                        part,\n"
                + "                        supplier,\n"
                + "                        lineitem,\n"
                + "                        partsupp,\n"
                + "                        orders,\n"
                + "                        nation\n"
                + "                where\n"
                + "                        s_suppkey = l_suppkey\n"
                + "                        and ps_suppkey = l_suppkey\n"
                + "                        and ps_partkey = l_partkey\n"
                + "                        and p_partkey = l_partkey\n"
                + "                        and o_orderkey = l_orderkey\n"
                + "                        and s_nationkey = n_nationkey\n"
                + "                        and p_name like '%green%'\n"
                + "        ) as profit\n"
                + "group by\n"
                + "        nation,\n"
                + "        o_year\n"
                + "order by\n"
                + "        nation,\n"
                + "        o_year desc;";
        executeQuery(sql);
    }

    public void query10() {
        String sql;
        sql = "select\n"
                + "        c_custkey,\n"
                + "        c_name,\n"
                + "        sum(l_extendedprice * (1 - l_discount)) as revenue,\n"
                + "        c_acctbal,\n"
                + "        n_name,\n"
                + "        c_address,\n"
                + "        c_phone,\n"
                + "        c_comment\n"
                + "from\n"
                + "        customer,\n"
                + "        orders,\n"
                + "        lineitem,\n"
                + "        nation\n"
                + "where\n"
                + "        c_custkey = o_custkey\n"
                + "        and l_orderkey = o_orderkey\n"
                + "        and o_orderdate >= date '1993-10-01'\n"
                + "        and o_orderdate < date '1993-10-01' + interval '3' month\n"
                + "        and l_returnflag = 'R'\n"
                + "        and c_nationkey = n_nationkey\n"
                + "group by\n"
                + "        c_custkey,\n"
                + "        c_name,\n"
                + "        c_acctbal,\n"
                + "        c_phone,\n"
                + "        n_name,\n"
                + "        c_address,\n"
                + "        c_comment\n"
                + "order by\n"
                + "        revenue desc\n"
                + "limit 20;";
        executeQuery(sql);
    }

    public void query11() {
        String sql;
        sql = "select\n"
                + "        ps_partkey,\n"
                + "        sum(ps_supplycost * ps_availqty) as value\n"
                + "from\n"
                + "        partsupp,\n"
                + "        supplier,\n"
                + "        nation\n"
                + "where\n"
                + "        ps_suppkey = s_suppkey\n"
                + "        and s_nationkey = n_nationkey\n"
                + "        and n_name = 'GERMANY'\n"
                + "group by\n"
                + "        ps_partkey having\n"
                + "                sum(ps_supplycost * ps_availqty) > (\n"
                + "                        select\n"
                + "                                sum(ps_supplycost * ps_availqty) * 0.0001000000\n"
                + "                        from\n"
                + "                                partsupp,\n"
                + "                                supplier,\n"
                + "                                nation\n"
                + "                        where\n"
                + "                                ps_suppkey = s_suppkey\n"
                + "                                and s_nationkey = n_nationkey\n"
                + "                                and n_name = 'GERMANY'\n"
                + "                )\n"
                + "order by\n"
                + "        value desc;";
        executeQuery(sql);
    }

    public void query12() {
        String sql;
        sql = "select\n"
                + "        l_shipmode,\n"
                + "        sum(case\n"
                + "                when o_orderpriority = '1-URGENT'\n"
                + "                        or o_orderpriority = '2-HIGH'\n"
                + "                        then 1\n"
                + "                else 0\n"
                + "        end) as high_line_count,\n"
                + "        sum(case\n"
                + "                when o_orderpriority <> '1-URGENT'\n"
                + "                        and o_orderpriority <> '2-HIGH'\n"
                + "                        then 1\n"
                + "                else 0\n"
                + "        end) as low_line_count\n"
                + "from\n"
                + "        orders,\n"
                + "        lineitem\n"
                + "where\n"
                + "        o_orderkey = l_orderkey\n"
                + "        and l_shipmode in ('MAIL', 'SHIP')\n"
                + "        and l_commitdate < l_receiptdate\n"
                + "        and l_shipdate < l_commitdate\n"
                + "        and l_receiptdate >= date '1994-01-01'\n"
                + "        and l_receiptdate < date '1994-01-01' + interval '1' year\n"
                + "group by\n"
                + "        l_shipmode\n"
                + "order by\n"
                + "        l_shipmode;";
        executeQuery(sql);
    }

    public void query13() {
        String sql;
        sql = "select\n"
                + "        c_count,\n"
                + "        count(*) as custdist\n"
                + "from\n"
                + "        (\n"
                + "                select\n"
                + "                        c_custkey,\n"
                + "                        count(o_orderkey)\n"
                + "                from\n"
                + "                        customer left outer join orders on\n"
                + "                                c_custkey = o_custkey\n"
                + "                                and o_comment not like '%special%requests%'\n"
                + "                group by\n"
                + "                        c_custkey\n"
                + "        ) as c_orders (c_custkey, c_count)\n"
                + "group by\n"
                + "        c_count\n"
                + "order by\n"
                + "        custdist desc,\n"
                + "        c_count desc;";
        executeQuery(sql);
    }

    public void query14() {
        String sql;
        sql = "select\n"
                + "        100.00 * sum(case\n"
                + "                when p_type like 'PROMO%'\n"
                + "                        then cast(l_extendedprice as double precision) * cast((1 - l_discount) as double precision)\n"
                + "                else 0\n"
                + "        end) / sum(cast(l_extendedprice as double precision) * cast((1 - l_discount) as double precision)) as promo_revenue\n"
                + "from\n"
                + "        lineitem,\n"
                + "        part\n"
                + "where\n"
                + "        l_partkey = p_partkey\n"
                + "        and l_shipdate >= date '1995-09-01'\n"
                + "        and l_shipdate < date '1995-09-01' + interval '1' month;";
        executeQuery(sql);
    }

    public void query15() {
        String sql;
        Statement st = null;
        sql = "create view revenue0 (supplier_no, total_revenue) as\n"
                + "        select\n"
                + "                l_suppkey,\n"
                + "                sum(l_extendedprice * (1 - l_discount))\n"
                + "        from\n"
                + "                lineitem\n"
                + "        where\n"
                + "                l_shipdate >= date '1996-01-01'\n"
                + "                and l_shipdate < date '1996-01-01' + interval '3' month\n"
                + "        group by\n"
                + "                l_suppkey;";
        try {
            st = con.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TPCHQueries.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            /*try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(TPCHQueries.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }

        sql = "select\n"
                + "        s_suppkey,\n"
                + "        s_name,\n"
                + "        s_address,\n"
                + "        s_phone,\n"
                + "        total_revenue\n"
                + "from\n"
                + "        supplier,\n"
                + "        revenue0\n"
                + "where\n"
                + "        s_suppkey = supplier_no\n"
                + "        and total_revenue = (\n"
                + "                select\n"
                + "                        max(total_revenue)\n"
                + "                from\n"
                + "                        revenue0\n"
                + "        )\n"
                + "order by\n"
                + "        s_suppkey;";
        executeQuery(sql);

        sql = "drop view revenue0;";

        try {
            st = con.createStatement();
            st.executeUpdate(sql);
            //st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TPCHQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void query16() {
        String sql;
        sql = "select\n"
                + "        p_brand,\n"
                + "        p_type,\n"
                + "        p_size,\n"
                + "        count(distinct ps_suppkey) as supplier_cnt\n"
                + "from\n"
                + "        partsupp,\n"
                + "        part\n"
                + "where\n"
                + "        p_partkey = ps_partkey\n"
                + "        and p_brand <> 'Brand#45'\n"
                + "        and p_type not like 'MEDIUM POLISHED%'\n"
                + "        and p_size in (49, 14, 23, 45, 19, 3, 36, 9)\n"
                + "        and ps_suppkey not in (\n"
                + "                select\n"
                + "                        s_suppkey\n"
                + "                from\n"
                + "                        supplier\n"
                + "                where\n"
                + "                        s_comment like '%Customer%Complaints%'\n"
                + "        )\n"
                + "group by\n"
                + "        p_brand,\n"
                + "        p_type,\n"
                + "        p_size\n"
                + "order by\n"
                + "        supplier_cnt desc,\n"
                + "        p_brand,\n"
                + "        p_type,\n"
                + "        p_size;";
        executeQuery(sql);
    }

    public void query17() {
        String sql;
        sql = "select\n"
                + "        sum(l_extendedprice) / 7.0 as avg_yearly\n"
                + "from\n"
                + "        lineitem,\n"
                + "        part\n"
                + "where\n"
                + "        p_partkey = l_partkey\n"
                + "        and p_brand = 'Brand#23'\n"
                + "        and p_container = 'MED BOX'\n"
                + "        and l_quantity < (\n"
                + "                select\n"
                + "                        0.2 * avg(l_quantity)\n"
                + "                from\n"
                + "                        lineitem\n"
                + "                where\n"
                + "                        l_partkey = p_partkey\n"
                + "        );";
        executeQuery(sql);
    }

    public void query18() {
        String sql;
        sql = "select\n"
                + "        c_name,\n"
                + "        c_custkey,\n"
                + "        o_orderkey,\n"
                + "        o_orderdate,\n"
                + "        o_totalprice,\n"
                + "        sum(l_quantity)\n"
                + "from\n"
                + "        customer,\n"
                + "        orders,\n"
                + "        lineitem\n"
                + "where\n"
                + "        o_orderkey in (\n"
                + "                select\n"
                + "                        l_orderkey\n"
                + "                from\n"
                + "                        lineitem\n"
                + "                group by\n"
                + "                        l_orderkey having\n"
                + "                                sum(l_quantity) > 300\n"
                + "        )\n"
                + "        and c_custkey = o_custkey\n"
                + "        and o_orderkey = l_orderkey\n"
                + "group by\n"
                + "        c_name,\n"
                + "        c_custkey,\n"
                + "        o_orderkey,\n"
                + "        o_orderdate,\n"
                + "        o_totalprice\n"
                + "order by\n"
                + "        o_totalprice desc,\n"
                + "        o_orderdate\n"
                + "limit 100;";
        executeQuery(sql);
    }

    public void query19() {
        String sql;
        sql = "select\n"
                + "        sum(l_extendedprice* (1 - l_discount)) as revenue\n"
                + "from\n"
                + "        lineitem,\n"
                + "        part\n"
                + "where\n"
                + "        (\n"
                + "                p_partkey = l_partkey\n"
                + "                and p_brand = 'Brand#12'\n"
                + "                and p_container in ('SM CASE', 'SM BOX', 'SM PACK', 'SM PKG')\n"
                + "                and l_quantity >= 1 and l_quantity <= 1 + 10\n"
                + "                and p_size between 1 and 5\n"
                + "                and l_shipmode in ('AIR', 'AIR REG')\n"
                + "                and l_shipinstruct = 'DELIVER IN PERSON'\n"
                + "        )\n"
                + "        or\n"
                + "        (\n"
                + "                p_partkey = l_partkey\n"
                + "                and p_brand = 'Brand#23'\n"
                + "                and p_container in ('MED BAG', 'MED BOX', 'MED PKG', 'MED PACK')\n"
                + "                and l_quantity >= 10 and l_quantity <= 10 + 10\n"
                + "                and p_size between 1 and 10\n"
                + "                and l_shipmode in ('AIR', 'AIR REG')\n"
                + "                and l_shipinstruct = 'DELIVER IN PERSON'\n"
                + "        )\n"
                + "        or\n"
                + "        (\n"
                + "                p_partkey = l_partkey\n"
                + "                and p_brand = 'Brand#34'\n"
                + "                and p_container in ('LG CASE', 'LG BOX', 'LG PACK', 'LG PKG')\n"
                + "                and l_quantity >= 20 and l_quantity <= 20 + 10\n"
                + "                and p_size between 1 and 15\n"
                + "                and l_shipmode in ('AIR', 'AIR REG')\n"
                + "                and l_shipinstruct = 'DELIVER IN PERSON'\n"
                + "        );";
        executeQuery(sql);
    }

    public void query20() {
        String sql;
        sql = "select\n"
                + "        s_name,\n"
                + "        s_address\n"
                + "from\n"
                + "        supplier,\n"
                + "        nation\n"
                + "where\n"
                + "        s_suppkey in (\n"
                + "                select\n"
                + "                        ps_suppkey\n"
                + "                from\n"
                + "                        partsupp\n"
                + "                where\n"
                + "                        ps_partkey in (\n"
                + "                                select\n"
                + "                                        p_partkey\n"
                + "                                from\n"
                + "                                        part\n"
                + "                                where\n"
                + "                                        p_name like 'forest%'\n"
                + "                        )\n"
                + "                        and ps_availqty > (\n"
                + "                                select\n"
                + "                                        0.5 * sum(l_quantity)\n"
                + "                                from\n"
                + "                                        lineitem\n"
                + "                                where\n"
                + "                                        l_partkey = ps_partkey\n"
                + "                                        and l_suppkey = ps_suppkey\n"
                + "                                        and l_shipdate >= date '1994-01-01'\n"
                + "                                        and l_shipdate < date '1994-01-01' + interval '1' year\n"
                + "                        )\n"
                + "        )\n"
                + "        and s_nationkey = n_nationkey\n"
                + "        and n_name = 'CANADA'\n"
                + "order by\n"
                + "        s_name;";
        executeQuery(sql);
    }

    public void query21() {
        String sql;
        sql = "select\n"
                + "        s_name,\n"
                + "        count(*) as numwait\n"
                + "from\n"
                + "        supplier,\n"
                + "        lineitem l1,\n"
                + "        orders,\n"
                + "        nation\n"
                + "where\n"
                + "        s_suppkey = l1.l_suppkey\n"
                + "        and o_orderkey = l1.l_orderkey\n"
                + "        and o_orderstatus = 'F'\n"
                + "        and l1.l_receiptdate > l1.l_commitdate\n"
                + "        and exists (\n"
                + "                select\n"
                + "                        *\n"
                + "                from\n"
                + "                        lineitem l2\n"
                + "                where\n"
                + "                        l2.l_orderkey = l1.l_orderkey\n"
                + "                        and l2.l_suppkey <> l1.l_suppkey\n"
                + "        )\n"
                + "        and not exists (\n"
                + "                select\n"
                + "                        *\n"
                + "                from\n"
                + "                        lineitem l3\n"
                + "                where\n"
                + "                        l3.l_orderkey = l1.l_orderkey\n"
                + "                        and l3.l_suppkey <> l1.l_suppkey\n"
                + "                        and l3.l_receiptdate > l3.l_commitdate\n"
                + "        )\n"
                + "        and s_nationkey = n_nationkey\n"
                + "        and n_name = 'SAUDI ARABIA'\n"
                + "group by\n"
                + "        s_name\n"
                + "order by\n"
                + "        numwait desc,\n"
                + "        s_name\n"
                + "limit 100;";
        executeQuery(sql);
    }

    public void query22() {
        String sql;
        sql = "select\n"
                + "        cntrycode,\n"
                + "        count(*) as numcust,\n"
                + "        sum(c_acctbal) as totacctbal\n"
                + "from\n"
                + "        (\n"
                + "                select\n"
                + "                        substring(c_phone from 1 for 2) as cntrycode,\n"
                + "                        c_acctbal\n"
                + "                from\n"
                + "                        customer\n"
                + "                where\n"
                + "                        substring(c_phone from 1 for 2) in\n"
                + "                                ('13', '31', '23', '29', '30', '18', '17')\n"
                + "                        and c_acctbal > (\n"
                + "                                select\n"
                + "                                        avg(c_acctbal)\n"
                + "                                from\n"
                + "                                        customer\n"
                + "                                where\n"
                + "                                        c_acctbal > 0.00\n"
                + "                                        and substring(c_phone from 1 for 2) in\n"
                + "                                                ('13', '31', '23', '29', '30', '18', '17')\n"
                + "                        )\n"
                + "                        and not exists (\n"
                + "                                select\n"
                + "                                        *\n"
                + "                                from\n"
                + "                                        orders\n"
                + "                                where\n"
                + "                                        o_custkey = c_custkey\n"
                + "                        )\n"
                + "        ) as custsale\n"
                + "group by\n"
                + "        cntrycode\n"
                + "order by\n"
                + "        cntrycode;";
        executeQuery(sql);
    }

}
