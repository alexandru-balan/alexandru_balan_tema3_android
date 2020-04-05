package alexandru.balan.tema3.interfaces

/**
 * A general type of click listener that the app uses to register clicks on the items of
 * recyclerviews used throughout the app
 */
interface ItemClickListener<ItemType> {
    fun onItemClick(item : ItemType, id : Int)

    fun onLongItemClick (item : ItemType, id: Int) : Boolean
}