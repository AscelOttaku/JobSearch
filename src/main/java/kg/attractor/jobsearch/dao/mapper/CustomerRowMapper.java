package kg.attractor.jobsearch.dao.mapper;

import kg.attractor.jobsearch.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class CustomerRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("userid"));
        user.setName(rs.getString("firstname"));
        user.setSurname(rs.getString("surname"));
        user.setAge(rs.getInt("age"));
        user.setEmail(rs.getString("email"));
        user.setAccountType(rs.getString("account_type"));
        user.setPassword(rs.getString("password"));
        user.setAvatar(rs.getString("avatar"));
        user.setPhoneNumber(rs.getString("phone_number"));
        return user;
    }
}
