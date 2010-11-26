package de.lmu.ifi.dbs.elki.database.datastore;

import de.lmu.ifi.dbs.elki.database.ids.DBID;
import de.lmu.ifi.dbs.elki.database.ids.DBIDRange;

/**
 * Mapping a static DBID range to storage IDs.
 * 
 * @author Erich Schubert
 */
public class RangeIDMap implements DataStoreIDMap {
  /**
   * Start offset
   */
  final DBIDRange range;
  
  /**
   * Constructor from a static DBID range allocation.
   * 
   * @param range DBID range to use
   */
  public RangeIDMap(DBIDRange range) {
    this.range = range;
  }

  @Override
  public int map(DBID dbid) {
    return range.getOffset(dbid);
  }
}
