package org.zalando.intellij.swagger.completion.field.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.Fields;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;

class RootCompletion extends FieldCompletion {

    RootCompletion(final SwaggerCompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    public void fill() {
        Fields.root().forEach(this::addUnique);
    }

}
