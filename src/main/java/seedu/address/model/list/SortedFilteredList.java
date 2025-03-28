//package seedu.address.model.list;
//
//import java.util.Comparator;
//import java.util.function.Predicate;
//
//import javafx.beans.NamedArg;
////import javafx.collections.ListChangeListener;
//import javafx.collections.ListChangeListener.Change;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.collections.transformation.TransformationList;
////import javafx.beans.property.ObjectProperty;
////import javafx.beans.property.ObjectPropertyBase;
////import javafx.collections.ObservableList;
//
////import com.sun.javafx.collections.NonIterableChange.GenericAddRemoveChange;
////import com.sun.javafx.collections.SortHelper;
//
//public class SortedFilteredList<E> extends TransformationList<E, E> {
//
//    private FilteredList<E> filteredList;
//    private SortedList<E> sortedList;
//
//    // Constructor with just the source ObservableList
//    public SortedFilteredList(@NamedArg("source") ObservableList<E> source) {
//        super(source);
//
//        // Set the default filter (always true) and default sorting (natural order)
//        this.filteredList = new FilteredList<>(source, e -> true); // No filtering, include all elements
//        this.sortedList = new SortedList<>(filteredList, Comparator.naturalOrder()); // Default sort (natural order)
//    }
//
//    @Override
//    protected void sourceChanged(Change<? extends E> c) {
//        filteredList.sourceChanged(c);
//        sortedList.sourceChanged(c);
//    }
//
//    @Override
//    public int size() {
//        return sortedList.size();
//    }
//
//    @Override
//    public E get(int index) {
//        return sortedList.get(index);
//    }
//
//    // Setter for predicate to update the filter condition
//    public void setPredicate(Predicate<? super E> predicate) {
//        filteredList.setPredicate(predicate);
//    }
//
//    // Setter for comparator to update the sorting order
//    public void setComparator(Comparator<? super E> comparator) {
//        sortedList.setComparator(comparator);
//    }
//
//    @Override
//    public int getSourceIndex(int index) {
//        return 0;
//    }
//
//    @Override
//    public int getViewIndex(int index) {
//        return 0;
//    }
//
//    @Override
//    public FilteredList<E> filtered(Predicate<E> predicate) {
//        return super.filtered(predicate);
//    }
//}
