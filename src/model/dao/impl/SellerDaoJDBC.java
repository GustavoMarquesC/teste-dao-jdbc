package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	Connection conn = null; //inicia a conexão no sellerDaoJDBC
	
	//assim não é preciso instanciar a conexão em todo método
	
	public SellerDaoJDBC (Connection conn) { //contrutor que recebe a conexao
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement( //chama o prepare statement já conectando
					//faz uma pesquisa com o banco
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department " 
					+ "ON seller.DepartmentId = department.Id " 
					+ "WHERE seller.Id = ?");
			//o st vai setar um valor inteiro no primeiro '?' quando receber um id  		
			st.setInt(1, id);
			rs = st.executeQuery(); //o rs vai receber o valor quando a pesquisa no banco for feita
			if (rs.next()) { //como o rs inicia na posição 0, ele precisa verificar quando ele for valido
				Department dep = new Department(); //dessa forma instancia um department
				dep.setId(rs.getInt("DepartmentId")); //o department vai setar um id com o valor inteiro que receber no rs como 'DepartmentID' (valor inteiro)
				dep.setName(rs.getString("DepName")); //o mesmo acontece com o nome, porém agora é uma string
				Seller obj = new Seller();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setEmail(rs.getString("Email"));
				obj.setBaseSalary(rs.getDouble("BaseSalary"));
				obj.setBirthDate(rs.getDate("BirthDate"));
				obj.setDepartment(dep);
				return obj;
			}
			return null;
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
