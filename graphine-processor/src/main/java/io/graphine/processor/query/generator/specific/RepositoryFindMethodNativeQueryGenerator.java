package io.graphine.processor.query.generator.specific;

import io.graphine.processor.metadata.model.entity.EntityMetadata;
import io.graphine.processor.metadata.model.entity.attribute.AttributeMetadata;
import io.graphine.processor.metadata.model.repository.method.MethodMetadata;
import io.graphine.processor.metadata.model.repository.method.name.QueryableMethodName;
import io.graphine.processor.metadata.model.repository.method.name.fragment.ConditionFragment;
import io.graphine.processor.metadata.model.repository.method.name.fragment.SortingFragment;
import io.graphine.processor.query.model.parameter.ComplexParameter;
import io.graphine.processor.query.model.parameter.Parameter;

import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.stream.Collectors;

import static io.graphine.processor.util.StringUtils.uncapitalize;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;

/**
 * @author Oleg Marchenko
 */
public final class RepositoryFindMethodNativeQueryGenerator extends RepositoryMethodNativeQueryGenerator {
    public RepositoryFindMethodNativeQueryGenerator(EntityMetadata entity) {
        super(entity);
    }

    @Override
    protected String generateQuery(MethodMetadata method) {
        String joinedColumns =
                entity.getAttributes()
                      .stream()
                      .map(AttributeMetadata::getColumn)
                      .collect(Collectors.joining(", "));

        StringBuilder queryBuilder = new StringBuilder()
                .append("SELECT ")
                .append(joinedColumns)
                .append(" FROM ")
                .append(entity.getQualifiedTable());

        QueryableMethodName queryableName = method.getQueryableName();

        ConditionFragment condition = queryableName.getCondition();
        if (nonNull(condition)) {
            queryBuilder.append(generateWhereClause(condition));
        }

        SortingFragment sorting = queryableName.getSorting();
        if (nonNull(sorting)) {
            queryBuilder.append(generateOrderClause(sorting));
        }

        return queryBuilder.toString();
    }

    private String generateOrderClause(SortingFragment sorting) {
        String joinedOrderColumns =
                sorting.getSorts()
                       .stream()
                       .map(sort -> {
                           AttributeMetadata attribute = entity.getAttribute(sort.getAttributeName());
                           return attribute.getColumn() + " " + sort.getDirection().name();
                       })
                       .collect(Collectors.joining(", "));
        return " ORDER BY " + joinedOrderColumns;
    }

    @Override
    protected List<Parameter> collectProducedParameters(MethodMetadata method) {
        Parameter parentParameter = new Parameter(uncapitalize(entity.getName()), entity.getNativeType());
        List<Parameter> childParameters =
                entity.getAttributes()
                      .stream()
                      .map(AttributeMetadata::getNativeElement)
                      .map(Parameter::basedOn)
                      .collect(Collectors.toList());
        return singletonList(new ComplexParameter(parentParameter, childParameters));
    }

    @Override
    protected List<Parameter> collectConsumedParameters(MethodMetadata method) {
        QueryableMethodName queryableName = method.getQueryableName();

        ConditionFragment condition = queryableName.getCondition();
        if (nonNull(condition)) {
            ExecutableElement methodElement = method.getNativeElement();
            return collectConditionParameters(condition, methodElement.getParameters());
        }
        return emptyList();
    }
}
