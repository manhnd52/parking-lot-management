package Group13.parking_lot_management.dao;

import java.util.List;

public interface DAOInterface<Entity> {
	public List<Entity> selectAll();
	// Do Student sử dụng MSSV làm khóa, nên dùng từ Key sẽ hợp lý hơn
	public Entity getByKey(int id);
	public boolean saveOrUpdate(Entity e);
	public boolean delete(Entity e);
}
