package com.github.camork

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupManager
import com.intellij.codeInsight.lookup.impl.LookupImpl
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.impl.EditorImpl

import java.awt.event.InputEvent
import java.awt.event.KeyEvent

/**
 * @author Charles Wu
 */
class InsertAction extends AnAction {

    @Override
    void actionPerformed(AnActionEvent e) {
        InputEvent inputEvent = e.getInputEvent()

        LookupManager lookupManager = LookupManager.getInstance(e?.getProject())

        LookupImpl activeLookup = (LookupImpl) lookupManager.getActiveLookup()

        if (inputEvent instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) inputEvent

            final char keyChar = keyEvent.getKeyChar()

            if (activeLookup != null) {
                List<LookupElement> items = activeLookup.getItems()

                int index = keyChar - ('0' as char) - 1

                if (index < items.size()) {
                    activeLookup.finishLookup('\n' as char, items.get(index))
                }
            } else {
                EditorImpl editor = (EditorImpl) CommonDataKeys.EDITOR.getData(e.getDataContext())

                editor?.type(String.valueOf(keyChar))
            }
        }
    }
}