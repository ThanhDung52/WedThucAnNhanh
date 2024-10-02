import { Chip, IconButton, useTheme } from "@mui/material";
import React from "react";
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { removeCartItem, updateCariItem } from "../State/Cart/Action";
import CloseIcon from '@mui/icons-material/Close';

const CartItem = ({ item }) => {
    const { auth, cart } = useSelector((store) => store)
    const navigate = useNavigate();
    const dispatch = useDispatch()
    const jwt = localStorage.getItem("jwt")
    const theme = useTheme();

    const handleUpdateCartItam = (value) => {
        if (value === -1 && item.quantity === 1) {
            handleRemoveCartItem()
        }
        const data = { cartItemId: item.id, quantity: item.quantity + value }
        dispatch(updateCariItem({ data, jwt }))
    }
    const handleRemoveCartItem = () => {
        dispatch(removeCartItem({ cartItemId: item.id, jwt: auth.jwt || jwt }))
    }


    return (        
        <div className="px-5">
          
            <div className="lg:flex items-center lg:space-x-5">
            <IconButton
                size="small"
                sx={{
                    position: "absolute",
                    left: 0,
                
                    outline: "none"

                }}
                onClick={() => handleRemoveCartItem(item.id)}>
                <CloseIcon sx={{ fontSize: "2rem" }} />
            </IconButton>

                <div>
                    <img className="w-[5rem] h-[5rem] object-cover"
                        src={item?.food.images[0]}
                        alt=""
                    />


                </div>

                <div className="flex items-center justify-between lg:w-[70%]">
                    <div className="space-y-1 lg:space-y-3 w-full" style={{ color: theme.palette.text.primary }}>



                        <p >{item.food.name}</p>


                        <div className="flex justify-between items-center">
                            <div className="flex items-center space-x-1">
                                <IconButton onClick={() => handleUpdateCartItam(-1)}>
                                    <RemoveCircleOutlineIcon />
                                </IconButton>
                                <div className="w-5 h-5 text-xs flex items-center justify-center">
                                    {item.quantity}
                                </div>
                                <IconButton onClick={() => handleUpdateCartItam(1)}>
                                    <AddCircleOutlineIcon />
                                </IconButton>
                            </div>
                        </div>

                    </div>


                    <p>${item.totalPrice}</p>


                </div>
            </div>
            <div className="pt-3 space-x-2">
                {item.ingredients.map((ingredient) => <Chip label={ingredient} />)}
            </div>
        </div>
    )
}

export default CartItem