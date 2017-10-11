
package com.iqmsoft.vertx.app.server.services;

import com.github.susom.database.Database;
import java.util.function.Supplier;


public class MessageDao {
  private final Supplier<Database> dbs;

  public MessageDao(Supplier<Database> dbs) {
    this.dbs = dbs;
  }

  public void addMessage(String message) {
    dbs.get().toInsert("insert into app_message (app_message_id, message) values (?,?)")
        .argPkSeq("app_pk_seq").argString(message).insert(1);
  }

  public Message findMessageById(Long messageId) {
    if (messageId == null) {
      return null;
    }

    return dbs.get().toSelect("select message from app_message where app_message_id=?")
        .argLong(messageId).queryOneOrNull(rs -> {
          Message result = new Message();
          result.messageId = messageId;
          result.message = rs.getStringOrNull();
          return result;
        });
  }

  public static class Message {
    public Long messageId;
    public String message;
  }
}
