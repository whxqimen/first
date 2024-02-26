package com.powernode.spring6;

import com.powernode.spring6.bean.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @program: 代码
 * @className: SpringJdbcTest
 * @version: V1.0
 * description:
 * @author: 王浩翔
 * create: 2023-02-28 20:33
 */


public class SpringTemplateTest {
    @Test
    public void testCallback(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "select id, real_name, age from t_user where id = ?";
        User user = jdbcTemplate.execute(sql, new PreparedStatementCallback<User>() {
            @Override
            public User doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                User user = null;
                ps.setInt(1, 1);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setRealName(rs.getString("real_name"));
                    user.setAge(rs.getInt("age"));
                }
                return user;
            }
        });
        System.out.println(user);
    }

    @Test
    public void testBatchDelete(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "delete from t_user where id = ?";
        Object[] o1 = {4};
        Object[] o2 = {5};
        Object[] o3 = {6};
        List<Object[]> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        list.add(o3);
        int[] ints = jdbcTemplate.batchUpdate(sql, list);
        System.out.println(Arrays.toString(ints));
    }

    @Test
    public void testBatchUpdate(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "update t_user set real_name=? ,age=? where id=?";
        Object[] obj1 ={"五菱1","33",4};
        Object[] obj2 ={"五菱2","33",5};
        Object[] obj3 ={"五菱3","33",6};
        List<Object[]> list = new ArrayList<>();
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        int[] count = jdbcTemplate.batchUpdate(sql, list);
        System.out.println(Arrays.toString(count));
    }

    @Test
    public void testBatchInsert(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "insert into t_user (id,real_name,age)values(?,?,?)";

        Object[] obj1 = {null,"芜湖",12};
        Object[] obj2 = {null,"南京",25};
        Object[] obj3 = {null,"杭州","24"};
        List<Object[]> list = new ArrayList<>();
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        int[] count = jdbcTemplate.batchUpdate(sql, list);
        System.out.println(count);
    }


    @Test
    public void testQueryOneValue(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "select count(*) from t_user";
        int count = jdbcTemplate.queryForObject(sql, int.class);
        System.out.println("总记录条数：" + count);
    }

    @Test
    public void testSelecAll(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "select id,real_name,age from t_user";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void testSelectById(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "select id,real_name,age from t_user where id = ?";
        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), 1);
        System.out.println(user);
    }

    @Test
    public void testDelete(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "delete from t_user where id = ?";
        int count = jdbcTemplate.update(sql, 2);
        System.out.println(count);

    }
    @Test
    public void testUpdate(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "update t_user set real_name = ?, age =? where id =?";
        int count = jdbcTemplate.update(sql, "乌哈", "15", 2);
        System.out.println(count);
    }

    @Test
    public void testInsert(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
        String sql = "insert into t_user(id,real_name,age)values(?,?,?)";
        int count = jdbcTemplate.update(sql, null, "小李", "18");
        System.out.println(count);
    }
}
