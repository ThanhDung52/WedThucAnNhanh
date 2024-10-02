import React from "react";
import CartItem from "./CartItem";
import { Box, Button, Card, Divider, Grid, Modal, TextField, useTheme } from "@mui/material";
import AddressCart from "./AddressCart";
import AddLocationAltIcon from '@mui/icons-material/AddLocationAlt';
import { ErrorMessage, Field, Form, Formik, useFormikContext } from "formik";
import { useDispatch, useSelector } from "react-redux";
import { createOrder } from "../State/Order/Action";
import * as Yup from "yup";
import { clearCartAction } from "../State/Cart/Action";

export const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    outline: 'none',
    boxShadow: 24,
    p: 4,
};
const initialValues = {
    streetAddress: "",
    stateProvince: "",
    postalCode: "",
    city: ""
}
const validationSchema = Yup.object({
    streetAddress: Yup.string().required("Street address is required"),
    stateProvince: Yup.string().required("State is required"),
    postalCode: Yup.string().required("Postal code is required"),
    city: Yup.string().required("City is required"),
});

const items = [1, 1]

const Cart = () => {
    const [open, setOpen] = React.useState(false);
    const handleClose = () => setOpen(false);
    const createOrderUsingSelectedAddress = () => { };
    const handleOpenAddressModel = () => setOpen(true);
    const { cart, auth } = useSelector(store => store)
    const dispatch = useDispatch();
    const theme = useTheme(); 

    console.log("log cart", cart);

    const handleSubmit = (values) => {
        const data = {
            jwt: localStorage.getItem("jwt"),
            order: {
                restaurantId: cart.cartItems[0].food?.restaurant.id,
                deliveryAddress: {
                    fullname: auth.user?.fullname,
                    streetAddress: values.streetAddress,
                    city: values.city,
                    stateProvince: values.stateProvince,
                    postalCode: values.postalCode,
                    country: "viet Nam"
                }
            }
        }
        dispatch(createOrder(data)).then(() => {
            // Sau khi tạo đơn hàng thành công, gọi action clear cart
            dispatch(clearCartAction());
        });

        
        console.log("data", data);

        console.log("form  value", values);

    }
    return (
        <div>
            <main className="lg:flex justify-between">
                <section className="lg:w-[30%] space-y-6 lg:min-h-screen pt-10">
                    {cart.cartItems?.map((item) => <CartItem item={item} />)}
                    <Divider />
                    <div className="billlDetails px-5 text-sm">
                        <p className="py-1.5" style={{ color: theme.palette.text.primary }}>Bill Details</p>
                        <div className="space-y-3">
                            <div className="flex justify-between text-gray-400"style={{ color: theme.palette.text.primary }}>
                                <p>Item Total</p>
                                <p>${cart.cart?.total}</p>
                            </div>
                            <div className="flex justify-between text-gray-400" style={{ color: theme.palette.text.primary }}>
                                <p>Deliver Fee</p>
                                <p>$21</p>
                            </div>
                            <div className="flex justify-between text-gray-400" style={{ color: theme.palette.text.primary }}>
                                <p>GST and Restaurant Charges</p>
                                <p>$33</p>
                            </div>
                            <Divider />
                        </div>
                        <div className="flex justify-between text-gray-400" style={{ color: theme.palette.text.primary }}>
                            <p>Total pay</p>
                            <p>${cart.cart?.total + 33 + 21}</p>
                        </div>
                    </div>
                </section>
                <Divider orientation="vertical" flexItem />
                <section className="lg:w-[70%] flex justify-center px-5 pb-10 lg:pb-0">
                    <div>
                        <h1 className="text-center font-semibold text-2xl py-10" style={{ color: theme.palette.text.primary }}>
                            Choose Delivery Address</h1>
                        <div className="flex gap-5 flex-wrap justify-center">
                            {[1, 1, 1, 1, 1, 1].map((item) => <AddressCart handleSelectAddress={createOrderUsingSelectedAddress} item={item} showButton={true} />)}

                            <Card className="flex gap-5 w-64 p-5"
                            sx={{
                                backgroundColor: theme.palette.background.paper, // Màu nền cho Card
                                color: theme.palette.text.primary, // Màu chữ
                                border: `1px solid ${theme.palette.divider}`, 
                             }}
                            >
                                <AddLocationAltIcon />
                                <div className="space-y-3 text-gray-500">
                                    <h1 className="font-semibold text-lg text-white" style={{ color: theme.palette.text.primary }}>Add new Address</h1>


                                    <Button variant="outlined" fullWidth onClick={handleOpenAddressModel}>Add</Button>

                                </div>
                            </Card>
                        </div>
                    </div>
                </section>
            </main>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Formik
                        initialValues={initialValues}
                        validationSchema={validationSchema}
                        onSubmit={handleSubmit}>
                                    {({ errors, touched, isValid, dirty  }) => (
                        <Form>
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <Field
                                        as={TextField}
                                        name="streetAddress"
                                        label="Street address, district"
                                        fullWidth
                                        variant="outlined"
                                        error={!!(touched.streetAddress && errors.streetAddress)}
                                        helperText={
                                            touched.streetAddress && errors.streetAddress ? (
                                                <span className="text-red-700">{errors.streetAddress}</span>
                                            ) : null
                                        }
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <Field
                                        as={TextField}
                                        name="stateProvince"
                                        label="stateProvince"
                                        fullWidth
                                        variant="outlined"
                                        error={!!(touched.stateProvince && errors.stateProvince)}
                                        helperText={
                                            touched.stateProvince && errors.stateProvince ? (
                                                <span className="text-red-700">{errors.stateProvince}</span>
                                            ) : null
                                        }

                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <Field
                                        as={TextField}
                                        name="city"
                                        label="city"
                                        fullWidth
                                        variant="outlined"
                                        error={!!(touched.city && errors.city)}
                                        helperText={
                                            touched.city && errors.city ? (
                                                <span className="text-red-700">{errors.city}</span>
                                            ) : null
                                        }
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <Field
                                        as={TextField}
                                        name="postalCode"
                                        label="postalCode"
                                        fullWidth
                                        error={!!(touched.postalCode && errors.postalCode)}
                                        helperText={
                                            touched.postalCode && errors.postalCode ? (
                                                <span className="text-red-700">{errors.postalCode}</span>
                                            ) : null
                                        }
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <Button fullWidth variant="contained" type="submit" color="primary"  disabled={!isValid || !dirty}>Deliver Here</Button>
                                </Grid>
                            </Grid>
                        </Form>
  )}
                    </Formik>
                </Box>
            </Modal>
        </div>
    )
}
export default Cart