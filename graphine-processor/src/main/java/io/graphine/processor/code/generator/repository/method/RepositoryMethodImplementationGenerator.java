package io.graphine.processor.code.generator.repository.method;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import io.graphine.core.util.UnnamedParameterUnwrapper;
import io.graphine.processor.code.renderer.PreparedStatementParameterRenderer;
import io.graphine.processor.code.renderer.parameter.IncrementalParameterIndexProvider;
import io.graphine.processor.code.renderer.parameter.NumericParameterIndexProvider;
import io.graphine.processor.code.renderer.parameter.ParameterIndexProvider;
import io.graphine.processor.metadata.model.repository.method.MethodMetadata;
import io.graphine.processor.query.model.NativeQuery;
import io.graphine.processor.query.model.parameter.Parameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Oleg Marchenko
 */
public abstract class RepositoryMethodImplementationGenerator {
    public final MethodSpec generate(MethodMetadata method, NativeQuery query) {
        return MethodSpec.overriding(method.getNativeElement())
                         .addCode(renderConnection(method, query))
                         .build();
    }

    protected CodeBlock renderConnection(MethodMetadata method, NativeQuery query) {
        return CodeBlock.builder()
                        .beginControlFlow("try ($T connection = dataSource.getConnection())",
                                          Connection.class)
                        .add(renderQuery(query))
                        .add(renderStatement(method, query))
                        .endControlFlow()
                        .beginControlFlow("catch ($T e)", SQLException.class)
                        .addStatement("throw new $T(e)", RuntimeException.class) // TODO: use a custom exception
                        .endControlFlow()
                        .build();
    }

    protected CodeBlock renderQuery(NativeQuery query) {
        List<Parameter> deferredParameters = query.getDeferredParameters();
        if (deferredParameters.isEmpty()) {
            return CodeBlock.builder()
                            .addStatement("$T query = $S", String.class, query.getValue())
                            .build();
        }
        else {
            List<CodeBlock> unnamedParameters =
                    deferredParameters.stream()
                                      .map(Parameter::getName)
                                      .map(parameterName ->
                                                   CodeBlock.of("$T.unwrapFor($L)",
                                                                UnnamedParameterUnwrapper.class, parameterName))
                                      .collect(Collectors.toList());
            return CodeBlock.builder()
                            .addStatement("$T query = $T.format($S, $L)",
                                          String.class, String.class, query.getValue(),
                                          CodeBlock.join(unnamedParameters, ", "))
                            .build();
        }
    }

    protected CodeBlock renderStatement(MethodMetadata method, NativeQuery query) {
        return CodeBlock.builder()
                        .beginControlFlow("try ($T statement = connection.prepareStatement(query))",
                                          PreparedStatement.class)
                        .add(renderStatementParameters(method, query))
                        .add(renderResultSet(method, query))
                        .endControlFlow()
                        .build();
    }

    protected CodeBlock renderStatementParameters(MethodMetadata method, NativeQuery query) {
        CodeBlock.Builder builder = CodeBlock.builder();

        List<Parameter> consumedParameters = query.getConsumedParameters();
        if (!consumedParameters.isEmpty()) {
            ParameterIndexProvider parameterIndexProvider;

            List<Parameter> deferredParameters = query.getDeferredParameters();
            if (deferredParameters.isEmpty()) {
                parameterIndexProvider = new NumericParameterIndexProvider();
            }
            else {
                builder.addStatement("int index = 1");
                parameterIndexProvider = new IncrementalParameterIndexProvider("index");
            }

            for (Parameter parameter : consumedParameters) {
                builder.add(parameter.accept(new PreparedStatementParameterRenderer(parameterIndexProvider)));
            }
        }

        return builder.build();
    }

    protected CodeBlock renderResultSet(MethodMetadata method, NativeQuery query) {
        return CodeBlock.builder()
                        .beginControlFlow("try ($T resultSet = statement.executeQuery())", ResultSet.class)
                        .add(renderResultSetParameters(method, query))
                        .endControlFlow()
                        .build();
    }

    protected CodeBlock renderResultSetParameters(MethodMetadata method, NativeQuery query) {
        return CodeBlock.builder().build();
    }
}
