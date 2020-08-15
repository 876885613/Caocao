package com.caocao.client.ui.adapter;

import com.caocao.client.R;
import com.caocao.client.ui.serve.bean.EditToolEntity;
import com.caocao.client.ui.serve.bean.ToolType;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Arrays;

public class EditToolAdapter extends BaseQuickAdapter<EditToolEntity, BaseViewHolder> {


    public EditToolAdapter(int layoutResId) {
        super(layoutResId, Arrays.asList(
                new EditToolEntity(ToolType.Image.name(), R.mipmap.insert_img),
                new EditToolEntity(ToolType.NewLine.name(), R.mipmap.new_line),
                new EditToolEntity(ToolType.TextColor.name(), R.mipmap.color),
                new EditToolEntity(ToolType.TextSizeAdd.name(), R.mipmap.text_size_add),
                new EditToolEntity(ToolType.TextSizeMin.name(), R.mipmap.text_size_min),
                new EditToolEntity(ToolType.Blod.name(), R.mipmap.bold),
                new EditToolEntity(ToolType.Italic.name(), R.mipmap.italic),
                new EditToolEntity(ToolType.Subscript.name(), R.mipmap.subscript),
                new EditToolEntity(ToolType.Superscript.name(), R.mipmap.superscript),
                new EditToolEntity(ToolType.Strikethrough.name(), R.mipmap.strikethrough),
                new EditToolEntity(ToolType.Underline.name(), R.mipmap.underline),
                new EditToolEntity(ToolType.JustifyLeft.name(), R.mipmap.justify_left),
                new EditToolEntity(ToolType.JustifyCenter.name(), R.mipmap.justify_center),
                new EditToolEntity(ToolType.JustifyRight.name(), R.mipmap.justify_right),
                new EditToolEntity(ToolType.Blockquote.name(), R.mipmap.blockquote),
                new EditToolEntity(ToolType.Undo.name(), R.mipmap.undo),
                new EditToolEntity(ToolType.Redo.name(), R.mipmap.redo),
                new EditToolEntity(ToolType.Indent.name(), R.mipmap.indent),
                new EditToolEntity(ToolType.Outdent.name(), R.mipmap.outdent),
//                new EditToolEntity(ToolType.InsertLink.name(), R.mipmap.insert_link),
                new EditToolEntity(ToolType.Checkbox.name(), R.mipmap.check_box),
                new EditToolEntity(ToolType.TextBackgroundColor.name(), R.mipmap.background_color),
//                new EditToolEntity(ToolType.FontSize.name(), R.mipmap.font_size),
                new EditToolEntity(ToolType.UnorderedList.name(), R.mipmap.unordered_list),
                new EditToolEntity(ToolType.OrderedList.name(), R.mipmap.ordered_list)
        ));
    }

    @Override
    protected void convert(BaseViewHolder helper, EditToolEntity item) {
        helper.setImageResource(R.id.iv_tool_ico, item.iconId);
    }
}
