// Generated code from Butter Knife. Do not modify!
package fi.oamk.chatclient;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatActivity_ViewBinding implements Unbinder {
  private ChatActivity target;

  private View view2131165185;

  @UiThread
  public ChatActivity_ViewBinding(ChatActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChatActivity_ViewBinding(final ChatActivity target, View source) {
    this.target = target;

    View view;
    target.etMessage = Utils.findRequiredViewAsType(source, R.id.etMessage, "field 'etMessage'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btnSend, "method 'onClickSend'");
    view2131165185 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickSend();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etMessage = null;

    view2131165185.setOnClickListener(null);
    view2131165185 = null;
  }
}
