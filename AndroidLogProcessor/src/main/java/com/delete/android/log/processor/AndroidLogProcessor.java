package com.delete.android.log.processor;

import com.sun.source.util.Trees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * 作者:created by storm
 *
 * Java抽象语法树AST，JCTree 分析
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class AndroidLogProcessor extends AbstractProcessor {

    private Messager messager;

    private Trees trees;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager=processingEnvironment.getMessager();
        if(processingEnvironment instanceof JavacProcessingEnvironment){
            JavacProcessingEnvironment javacProcessingEnvironment=(JavacProcessingEnvironment) processingEnvironment;
            this.trees=Trees.instance(javacProcessingEnvironment);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(!roundEnvironment.processingOver()&&trees!=null){
            roundEnvironment.getRootElements().stream().filter(it->it.getKind()== ElementKind.CLASS)
                    .forEach(it-> ((JCTree)trees.getTree(it)).accept(new DeleteLogTranslator()));
        }
        return false;
    }

    class DeleteLogTranslator extends TreeTranslator {

        public static final String LOG_TAG = "Log.";

        @Override
        public void visitBlock(JCTree.JCBlock jcBlock) {
            super.visitBlock(jcBlock);
            List<JCTree.JCStatement> jcStatementList = jcBlock.getStatements();
            if (jcStatementList == null || jcStatementList.isEmpty()) {
                return;
            }
            List<JCTree.JCStatement> out=List.nil();
            for (JCTree.JCStatement jcStatement : jcStatementList) {
                if (isLogStatements(jcStatement)) {
                    messager.printMessage(Diagnostic.Kind.WARNING, this.getClass().getCanonicalName()+" >>>>>> " + jcStatement.toString());
                } else {
                    out = out.append(jcStatement);
                }
            }
            jcBlock.stats = out;

            for (JCTree.JCStatement jcStatement : jcBlock.stats) {
                System.out.println(" ****** "+jcStatement.toString());
            }
        }


        private boolean isLogStatements(JCTree.JCStatement jcStatement) {
            return jcStatement.toString().contains(LOG_TAG);
        }
    }
}
