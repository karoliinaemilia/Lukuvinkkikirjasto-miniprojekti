
package lukuvinkkiKirjasto.dao;

import lukuvinkkiKirjasto.database.Database;
import java.sql.SQLException;
import java.util.List;
import lukuvinkkiKirjasto.domain.Kirja;

public class KirjaDao implements Dao<Kirja, Integer> {
    
    private Database database;

    public KirjaDao(Database database) {
        this.database = database;
    }

    @Override
    public Kirja findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Kirja> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Kirja saveOrUpdate(Kirja object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
