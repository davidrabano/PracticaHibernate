package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ejercicioHibernate1.ejercicioHibernate1.HibernateUtil;
import entity.Empleado;


public class EmpleadoDAO {
	
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
	public static void insertEmpleado(Empleado empleado) {
		try {
			iniciarSesion();
			s.save(empleado);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "insertEmpleado"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		} 
	}
	
	// Read
	public static Empleado getEmpleado(int id) {	
		Empleado empleado=new Empleado();
		try {
			iniciarSesion();
			empleado=s.get(Empleado.class, id);		
			// tx.commit(); Aquí no hace falta porque no se modifica la tabla.
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "getEmpleado"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		}
		return empleado;
	}
	
	// Update
	public static void updateEmpleado(Empleado empleado) {
		try {
			iniciarSesion();
			s.update(empleado);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "updateEmpleado"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		} 
	}
	
	// Delete
	public static void deleteEmpleado(Empleado empleado) {
		try {
			iniciarSesion();
			s.delete(empleado);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "deleteEmpleado"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		} 
	}
	
	// Devuelve la lista de registros de la tabla empleado
	public static List<Empleado> getAllEmpleados() {
		List<Empleado> empleadoList=null;
		
		try {
			iniciarSesion();
			//String hQuery = "from Empleado"; // Sin ordenar
			//String hQuery = "from Empleado order by codigo desc"; // Ordenado por código (de mayor a menor).
			String hQuery = "from Empleado order by codigo"; // Ordenado por código (de menor a mayor)
			empleadoList = s.createQuery(hQuery, Empleado.class).list();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "getAllEmpleados"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		} 	   	   			           
		return empleadoList;
	}
	
	// Devuelve true/false si existe o no existe un empleado con el parámetro id
	public static boolean existeEmpleado(int id) {
		boolean existe=false;
		
		try {
			iniciarSesion();
			List<Empleado> empleadosTabla = getAllEmpleados();
			for (Empleado empleado : empleadosTabla) {
				if (empleado.getCodigo() == id) {
					existe = true;
				}
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
				//logger.error(String.format("%1$s: Error en la capa de acceso a datos.", "insertEmpleado"), e);
		}
		finally {
			if (s != null) {
				s.close();
			}
		} 	   	   			   
		return existe;
	}
	
}

//VERSION CON session COMO PARÁMETRO
//
//package dao;
//
//import java.util.List;
//
//import org.hibernate.Session;
//
//import entity.Empleado;
//
//
//
//public class EmpleadoDAO {
//	
//	// Create
//	public static void insertEmpleado(Session s, Empleado empleado) {
//		s.save(empleado);
//	}
//	
//	// Read
//	public static Empleado getEmpleado(Session s, int id) {
//		return s.get(Empleado.class, id);
//	}
//	
//	// Update
//	public static void updateEmpleado(Session s, Empleado empleado) {
//		s.update(empleado);
//	}
//	
//	// Delete
//	public static void deleteEmpleado(Session s, Empleado empleado) {
//		s.delete(empleado);
//	}
//	
//	// Devuelve la lista de registros de la tabla empleado
//	public static List<Empleado> getAllEmpleados(Session s) {
//		//hQuery = "from Empleado"; // Sin ordenar
//		//hQuery = "from Empleado order by codigo desc"; // Ordenado por código (de mayor a menor).
//		String hQuery = "from Empleado order by codigo"; // Ordenado por código (de menor a mayor)
//		
//		List<Empleado> empleadoList = s.createQuery(hQuery, Empleado.class).list();
//				   	   			           
//		return empleadoList;
//	}
//}
