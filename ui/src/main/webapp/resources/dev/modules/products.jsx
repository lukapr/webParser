import React, {Component, PropTypes} from 'react';

import request from 'superagent';

import {List, ListItem} from 'material-ui/List';
import Avatar from 'material-ui/Avatar';
import {ErrorNotification, SuccessNotification} from "./notifications.jsx";

export default class Products extends Component {
    constructor(props) {
        super(props);
        this.state = {
            products: []
        };
    }

    //
    componentWillReceiveProps(nextProps) {
        if (nextProps.load) {
            this.loadFromServer();
        }
    };

    //
    loadFromServer = () => {
        request.get("results/products")
            .end((err, res) => {
                if (err || !res.ok) {
                    this.refs.errorNotification.handleOpen('Error during getting products: ' + err);
                } else {
                    this.setState({
                        products: res.body
                    });
                }
            })
    };

    render() {
        const result = [];
        this.state.products.forEach((product) => {
            result.push(<ListItem primaryText={product
                    .name} leftAvatar={
                    <Avatar src={product.img} size={50}/>
                }/>
            )
        });
        return (<div>
                <ErrorNotification ref='errorNotification'/>
                <SuccessNotification ref='successNotification'/>
                <List>
                    {result}
                </List>
            </div>
        )
    }

}