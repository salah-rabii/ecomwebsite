


import React, { useState, useEffect } from "react";
import Catg from "./Catg";
import ShopCart from "./ShopCart";
import { searchProductsByName } from "../../services/productService"; // <-- Assure-toi que ce chemin est correct
import "./style.css";

const Shop = ({ CartItem, addToCart, decreaseQty, addToWishlist, removeFromWishlist, isInWishlist, shopItems }) => {
  const [selectedCategory, setSelectedCategory] = useState("all");
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [isSearching, setIsSearching] = useState(false);

  const filteredItems =
    selectedCategory === "all"
      ? shopItems
      : shopItems.filter(
          (item) =>
            item.category &&
            item.category.toLowerCase() === selectedCategory.toLowerCase()
        );

  useEffect(() => {
    const fetchSearchResults = async () => {
      if (searchTerm.trim() === "") {
        setSearchResults([]);
        setIsSearching(false);
        return;
      }

      try {
        const results = await searchProductsByName(searchTerm);
        setSearchResults(results);
        setIsSearching(true);
      } catch (error) {
        console.error("Erreur lors de la recherche :", error.message);
        setIsSearching(false);
      }
    };

    fetchSearchResults();
  }, [searchTerm]);

  return (
    <>
      <section className="shop background">
        <div className="container d_flex">
          <Catg setCategory={setSelectedCategory} />

          <div className="contentWidth">
            <div className="heading d_flex">
              <div className="heading-left row f_flex">
                <h2>Shop</h2>
              </div>

              <div className="heading-right row">
                <input
                  type="text"
                  placeholder=" Rechercher un produit..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="search-input"
                />
              </div>
            </div>

            <div className="product-content grid1">
              {(isSearching ? searchResults : filteredItems).length > 0 ? (
                <ShopCart
                  shopItems={isSearching ? searchResults : filteredItems}
                  addToCart={addToCart}
                  addToWishlist={addToWishlist}
                  removeFromWishlist={removeFromWishlist}
                  isInWishlist={isInWishlist}
                />
              ) : (
                <div className="no-products">🛒 Aucun produit trouvé.</div>
              )}
            </div>

          </div>
        </div>
      </section>
    </>
  );
};

export default Shop;
