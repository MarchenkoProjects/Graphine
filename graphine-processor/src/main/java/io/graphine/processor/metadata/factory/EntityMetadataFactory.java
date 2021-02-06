package io.graphine.processor.metadata.factory;

import io.graphine.core.annotation.Entity;
import io.graphine.processor.metadata.model.entity.EntityMetadata;
import io.graphine.processor.metadata.model.entity.attribute.AttributeMetadata;
import io.graphine.processor.metadata.model.entity.attribute.IdentifierMetadata;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

import static io.graphine.processor.util.StringUtils.*;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.util.ElementFilter.fieldsIn;

/**
 * @author Oleg Marchenko
 */
public final class EntityMetadataFactory {
    private final AttributeMetadataFactory attributeMetadataFactory;

    public EntityMetadataFactory(AttributeMetadataFactory attributeMetadataFactory) {
        this.attributeMetadataFactory = attributeMetadataFactory;
    }

    public EntityMetadata createEntity(TypeElement element) {
        Entity entity = element.getAnnotation(Entity.class);

        String schema = entity.schema();
        if (isEmpty(schema)) {
            // TODO: use the default scheme from processor options
            schema = EMPTY;
        }

        String table = entity.table();
        if (isEmpty(table)) {
            // TODO: use table naming strategy
            table = uncapitalize(element.getSimpleName().toString());
        }

        List<VariableElement> fields = fieldsIn(element.getEnclosedElements());

        IdentifierMetadata identifier = fields
                .stream()
                .filter(IdentifierMetadata::isIdentifier)
                .findFirst()
                .map(attributeMetadataFactory::createIdentifier)
                .orElse(null);

        List<AttributeMetadata> attributes = fields
                .stream()
                .filter(AttributeMetadata::isAttribute)
                .map(attributeMetadataFactory::createAttribute)
                .collect(toList());

        return new EntityMetadata(element, schema, table, identifier, attributes);
    }
}
