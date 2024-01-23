package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj; 
			}
			return null; //o retorno do null é caso o rs.next() seja 0
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage()); //vai propagar o exception db
		}
		finally { //fechar o st e o rs
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{
		Seller obj = new Seller(); 
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department(); 
		dep.setId(rs.getInt("DepartmentId")); 
		dep.setName(rs.getString("DepName")); 
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement( //chama o prepare statement já conectando
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department " 
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name ");
			//o st vai setar um valor inteiro no primeiro '?' quando receber um id  		
			st.setInt(1, department.getId());
			rs = st.executeQuery(); //o rs vai receber o valor quando a pesquisa no banco for feita
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) { //como o rs inicia na posição 0, ele precisa verificar quando ele for valido
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj); 
			}
			return list; //o retorno do null é caso o rs.next() seja 0
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage()); //vai propagar o exception db
		}
		finally { //fechar o st e o rs
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
