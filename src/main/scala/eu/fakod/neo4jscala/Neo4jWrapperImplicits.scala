package eu.fakod.neo4jscala

import scala.language.implicitConversions

import org.neo4j.graphdb._
import org.neo4j.graphdb.traversal.{BranchState, Evaluation, Evaluator, PathEvaluator}

/**
 * trait for implicits
 * used by Neo4j wrapper
 *
 * @author Christopher Schmidt
 */
trait Neo4jWrapperImplicits {

  /**
   * converts to a relationship builder to use --> <-- methods
   */
  implicit def node2relationshipBuilder(node: Node) = new NodeRelationshipMethods(node)

  /**
   * converts a String to a relationship type
   */
  implicit def string2RelationshipType(relType: String) = DynamicRelationshipType.withName(relType)

  /**
   * conversion to use property set and get convenience
   */
  implicit def propertyContainer2RichPropertyContainer(propertyContainer: PropertyContainer) = new RichPropertyContainer(propertyContainer)

  /**
   * creates a functional correct StopEvaluator instance
   */
  @deprecated(message = "deprecated because of deprecation of StopEvaluator", since = "0.3.1")
  implicit def fn2StopEvaluator(e: TraversalPosition => Boolean) =
    new StopEvaluator() {
      def isStopNode(traversalPosition: TraversalPosition) = e(traversalPosition)
    }

  /**
   * creates a functional correct ReturnableEvaluator instance
   */
  @deprecated(message = "deprecated because of deprecation of ReturnableEvaluator", since = "0.3.1")
  implicit def fn2ReturnableEvaluator(e: TraversalPosition => Boolean) =
    new ReturnableEvaluator() {
      def isReturnableNode(traversalPosition: TraversalPosition) = e(traversalPosition)
    }

  /**
   * Converts a function to Evaluator instance
   * @since 0.3.1
   */
  implicit def fn2Evaluator(e: Path => Evaluation): Evaluator = new Evaluator {
    def evaluate(path: Path): Evaluation = e(path)
  }

  /**
   * Converts a function to PathEvaluator instance
   * @since 0.3.1
   */
  implicit def fn2PathEvaluator[A](e: (Path, BranchState[A])  => Evaluation): PathEvaluator[A] = new PathEvaluator.Adapter[A] {
    def evaluate(path: Path, state: BranchState[A]): Evaluation = e(path, state)
  }

  /**
   * Stuff for Indexes
   */
  implicit def indexManager(implicit ds: DatabaseService) = ds.gds.index

  /**
   * for serialization
   */
  implicit class NodeToCaseClass(pc: PropertyContainer) {
    def toCC[T: Manifest]: Option[T] = Neo4jWrapper.toCC[T](pc)

    def toCCPossible[T: Manifest]: Boolean = Neo4jWrapper.toCCPossible[T](pc)
  }
}
