package fr.inria.jfilter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 13/02/13
 * Time: 09:52
 */
public class FilterComponentTestCase extends FilterTestCase {

    public static class FakeComponentModel {

        public FakeComponentModel() {

            Component c = new Component();
            c.setName("Logger");

            Node n1 = new Node();
            n1.setName("node1");

            Node n2 = new Node();
            n2.setName("node2");

            _nodes.add(n1);
            _nodes.add(n2);

            n1.getComponents().add(c);

        }

        private List<Node> _nodes = new ArrayList<Node>();

        public List<Node> getNodes() {
            return _nodes;
        }

    }

    public static class NamedEntity {
        private String _name;

        public String getName() {
            return _name;
        }

        public void setName(String _n) {
            this._name = _n;
        }
    }

    public static class Node extends NamedEntity {

        private List<Component> _components = new ArrayList<Component>();

        public List<Component> getComponents() {
            return _components;
        }

    }

    public static class Component extends NamedEntity {

    }

    public FilterComponentTestCase(String name) {
        super(name);
    }


}
