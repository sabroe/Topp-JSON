package com.yelstream.topp.jackson.databind;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.FloatNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.POJONode;
import com.fasterxml.jackson.databind.node.ShortNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;
import java.util.Map;

/**
 * Visitor for traversing instances of {@link JsonNode}.
 * Intercepting nodes as a pre- or post-action is done by overriding any of the {@code onXXX()} methods in a sub-class
 * while still invoking the method of the same name in this the super-class.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
public class JsonNodeWalker {
    /**
     *
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder(builderClassName="Builder",toBuilder=true)
    public static class Context {
        private final JsonNode rootNode;
        private final JsonNode parentNode;
        private final JsonPointer nodePointer;
        private final String name;
    }

    public void walk(JsonNode node) {
        JsonPointer nodePointer=JsonPointer.valueOf(""+JsonPointer.SEPARATOR);
        Context context=Context.builder().rootNode(node).nodePointer(nodePointer).build();
        onJsonNode(context,node);
    }

    protected void onJsonNode(Context context,
                              JsonNode node) {
        if (node instanceof ContainerNode) {
            ContainerNode<?> n=(ContainerNode<?>)node;
            onContainerNode(context,n);
        } else {
            if (node instanceof ValueNode) {
                ValueNode n=(ValueNode)node;
                onValueNode(context,n);
            } else {
                throw new IllegalStateException(String.format("Failure to walk tree; cannot recognize node %s!",node));
            }
        }
    }

    protected void onContainerNode(Context context,
                                   ContainerNode<?> node) {
        if (node instanceof ArrayNode) {
            ArrayNode n=(ArrayNode)node;
            onArrayNode(context,n);
        } else {
            if (node instanceof ObjectNode) {
                ObjectNode n=(ObjectNode)node;
                onObjectNode(context,n);
            } else {
                throw new IllegalStateException(String.format("Failure to walk tree; cannot recognize node %s!",node));
            }
        }
    }

    protected void onArrayNode(Context context,
                               ArrayNode node) {
        Iterator<JsonNode> elements=node.elements();
        if (elements!=null) {
            int index=0;
            while (elements.hasNext()) {
                JsonNode element=elements.next();
                String name=Integer.toString(index);
                JsonPointer p=context.nodePointer.append(JsonPointer.valueOf(JsonPointer.SEPARATOR+name));
                Context c=context.toBuilder().parentNode(node).nodePointer(p).name(name).build();
                onJsonNode(c,element);
                index++;
            }
        }
    }

    protected void onObjectNode(Context context,
                                ObjectNode node) {
        Iterator<Map.Entry<String,JsonNode>> fields=node.fields();
        if (fields!=null) {
            while (fields.hasNext()) {
                Map.Entry<String,JsonNode> field=fields.next();
                String name=field.getKey();
                JsonPointer p=context.nodePointer.append(JsonPointer.valueOf(JsonPointer.SEPARATOR+name));
                Context c=context.toBuilder().parentNode(node).nodePointer(p).name(name).build();
                onJsonNode(c,field.getValue());
            }
        }
    }

    protected void onValueNode(Context context,
                               ValueNode node) {
        if (node instanceof NumericNode) {
            NumericNode n=(NumericNode)node;
            onNumericNode(context,n);
        } else {
            if (node instanceof NullNode) {
                NullNode n=(NullNode)node;
                onNullNode(context,n);
            } else {
                if (node instanceof MissingNode) {
                    MissingNode n=(MissingNode)node;
                    onMissingNode(context,n);
                } else {
                    if (node instanceof BooleanNode) {
                        BooleanNode n=(BooleanNode)node;
                        onBooleanNode(context,n);
                    } else {
                        if (node instanceof TextNode) {
                            TextNode n=(TextNode)node;
                            onTextNode(context,n);
                        } else {
                            if (node instanceof POJONode) {
                                POJONode n=(POJONode)node;
                                onPOJONode(context,n);
                            } else {
                                if (node instanceof BinaryNode) {
                                    BinaryNode n=(BinaryNode)node;
                                    onBinaryNode(context,n);
                                } else {
                                    throw new IllegalStateException(String.format("Failure to walk tree; cannot recognize node %s!",node));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected void onNumericNode(Context context,
                                 NumericNode node) {
        if (node instanceof DoubleNode) {
            DoubleNode n=(DoubleNode)node;
            onDoubleNode(context,n);
        } else {
            if (node instanceof FloatNode) {
                FloatNode n=(FloatNode)node;
                onFloatNode(context,n);
            } else {
                if (node instanceof IntNode) {
                    IntNode n=(IntNode)node;
                    onIntNode(context,n);
                } else {
                    if (node instanceof BigIntegerNode) {
                        BigIntegerNode n=(BigIntegerNode)node;
                        onBigIntegerNode(context,n);
                    } else {
                        if (node instanceof DecimalNode) {
                            DecimalNode n=(DecimalNode)node;
                            onDecimalNode(context,n);
                        } else {
                            if (node instanceof ShortNode) {
                                ShortNode n=(ShortNode)node;
                                onShortNode(context,n);
                            } else {
                                if (node instanceof LongNode) {
                                    LongNode n=(LongNode)node;
0                                    onLongNode(context,n);
                                } else {
                                    throw new IllegalStateException(String.format("Failure to walk tree; cannot recognize node %s!",node));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected void onDoubleNode(Context context,
                                DoubleNode node) {
    }

    protected void onFloatNode(Context context,
                               FloatNode node) {
    }

    protected void onIntNode(Context context,
                             IntNode node) {
    }

    protected void onBigIntegerNode(Context context,
                                    BigIntegerNode node) {
    }

    protected void onDecimalNode(Context context,
                                 DecimalNode node) {
    }

    protected void onShortNode(Context context,
                               ShortNode node) {
    }

    protected void onLongNode(Context context,
                              LongNode node) {
    }

    protected void onNullNode(Context context,
                              NullNode node) {
    }

    protected void onMissingNode(Context context,
                                 MissingNode node) {
    }

    protected void onBooleanNode(Context context,
                                 BooleanNode node) {
    }

    protected void onTextNode(Context context,
                              TextNode node) {
    }

    protected void onPOJONode(Context context,
                              POJONode node) {
    }

    protected void onBinaryNode(Context context,
                                BinaryNode node) {
    }
}
