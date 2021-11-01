package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ejercicioHibernate1.ejercicioHibernate1.HibernateUtil;
import entity.Departamento;

public class DepartamentoDAO {
	
	private static Session s;
    private static Transaction tx;
    
    private static void iniciarSesion() {
    	try {
    		s=HibernateUtil.getSessionFactory().openSession(); // Copiada la clase HibernateUtil de su proyecto
			tx=s.beginTransaction();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			  //logger.error(String.format("%1$s: Error al iniciar sesion.", "iniciarSesion"), e);
		}
    }
	
	// Create
	public static void insertDepartamento(Departamento departamento) {
		try {
			iniciarSesion();
			s.save(departamento);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "insertDepartamento"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		} 
	}
	
	// Read
	public static Departamento getDepartamento(int id) {	
		Departamento departamento=new Departamento();
		try {
			iniciarSesion();
			departamento=s.get(Departamento.class, id);		
			// tx.commit(); Aquí no hace falta porque no se modifica la tabla.
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "getDepartamento"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		}
		return departamento;
	}
	
	// Update
	public static void updateDepartamento(Departamento departamento) {
		try {
			iniciarSesion();
			s.update(departamento);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "updateDepartamento"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		} 
	}
	
	// Delete
	public static void deleteDepartamento(Departamento departamento) {
		try {
			iniciarSesion();
			s.delete(departamento);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "deleteDepartamento"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		} 
	}
	
	// Devuelve la lista de registros en la tabla departamento
	public static List<Departamento> getAllDepartamentos() {
		List<Departamento> departamentoList=null;
		
		try {
			iniciarSesion();
			//String hQuery = "from Departamento"; // Sin ordenar
			//String hQuery = "from Departamento order by codigo desc"; // Ordenado por código (de mayor a menor)
			String hQuery = "from Departamento order by codigo"; // Ordenado por código (de menor a mayor)
			departamentoList = s.createQuery(hQuery, Departamento.class).list();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "getAllDepartamentos"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		} 	   	   			           
		return departamentoList;
	}
	
	// Devuelve true/false si existe o no existe un departamento con el parámetro id
	public static boolean existeDepartamento(int id) {
		boolean existe=false;
		
		try {
			iniciarSesion();
			List<Departamento> departamentosTabla = getAllDepartamentos();
			for (Departamento departamento : departamentosTabla) {
				if (departamento.getCodigo() == id) {
					existe = true;
				}
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "existeDepartamento"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		} 	   	   			   
		return existe;
	}
	
}

// VERSION CON session COMO PARÁMETRO
// 
//package dao;
//
//import java.util.List;
//
//import org.hibernate.Session;
//
//import entity.Departamento;
//
//public class DepartamentoDAO {
//	
//	// Create
//	public static void insertDepartamento(Session s, Departamento departamento) {
//		s.save(departamento);
//	}
//	
//	// Read
//	public static Departamento getDepartamento(Session s, int id) {
//		return s.get(Departamento.class, id);
//	}
//	
//	// Update
//	public static void updateDepartamento(Session s, Departamento departamento) {
//		s.update(departamento);
//	}
//	
//	// Delete
//	public static void deleteDepartamento(Session s, Departamento departamento) {
//		s.delete(departamento);
//	}
//	
//	// Devuelve la lista de registros en la tabla departamento
//	public static List<Departamento> getAllDepartamentos(Session s) {
//		//String hQuery = "from Departamento"; // Sin ordenar
//		String hQuery = "from Departamento order by codigo";
//		List<Departamento> departamentoList = s.createQuery(hQuery, Departamento.class).list();
//				   	   			           
//		return departamentoList;
//	}
//
//}