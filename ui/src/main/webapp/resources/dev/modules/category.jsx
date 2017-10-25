import React, {Component, PropTypes} from 'react';

import request from 'superagent';
import {List, ListItem} from 'material-ui/List';
import Paper from 'material-ui/Paper';
import Lightbox from 'react-image-lightbox';
import {
    Table,
    TableBody,
    TableHeader,
    TableHeaderColumn,
    TableRow, TableRowColumn
} from 'material-ui/Table';


export default class Category extends Component {
    constructor(props) {
        super(props);
        this.state = {
            categories: [],
            products: new Map(),
            productsForCategory: [],
            img: ""
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
        request.get("results/categories")
            .end((err, res) => {
                if (err || !res.ok) {
                    // this.refs.errorNotification.handleOpen('Error during getting categories: ' + err);
                } else {
                    const categories = [];

                    const products = new Map();
                    res.body.forEach((category) => {
                        categories.push(<ListItem key={category.id} primaryText={category.name}
                                                  onClick={(e) => this.handleClick(category.id)}/>);
                        products.set(category.id, category.products);
                    });
                    this.setState({
                        categories: categories,
                        products: products
                    });
                }
            })
    };

    buildMap = (obj) => {
        return Object.keys(obj).reduce((map, key) => map.set(key, obj[key]), new Map());
    };

    handleClick = (categoryId) => {
        console.log(categoryId);
        console.log(this.state.products.get(categoryId));
        const productsForCategory = [];

        this.buildMap(this.state.products.get(categoryId)).forEach((product) => {
            productsForCategory.push(product);
        });
        this.setState({
            productsForCategory: productsForCategory
        });
    };

    handleClickImage = (imgHref) => {
        this.setState({
            img: imgHref
        });
    };

    render() {
        const style = {
            margin: 20,
            textAlign: 'center',
            display: 'inline-block',
        };
        const styles = {
            root: {
                display: 'flex',
                flexWrap: 'wrap',
            },
        };
        let table;
        if (this.state.productsForCategory.length > 0) {
            const result = [];
            this.state.productsForCategory.forEach((product) => {
                result.push(<TableRow>
                        <TableRowColumn style={{width: '32%'}}>
                            <img src={product.img} height="100" onClick={(e) => this.handleClickImage(product.img)}/>
                        </TableRowColumn>
                        <TableRowColumn style={{width: '34%'}}>{product.name}</TableRowColumn>
                        <TableRowColumn style={{width: '34%'}}>{product.article}</TableRowColumn>
                    </TableRow>
                )
            });
            table = <Table fixedHeader={true} style={{tableLayout: 'auto'}} height={'800px'}>
                <TableHeader adjustForCheckbox={false} displaySelectAll={false}>
                    <TableRow>
                        <TableHeaderColumn style={{width: '32%'}}>Image</TableHeaderColumn>
                        <TableHeaderColumn style={{width: '34%'}}>Name</TableHeaderColumn>
                        <TableHeaderColumn style={{width: '34%'}}>Article</TableHeaderColumn>
                    </TableRow>
                </TableHeader>
                <TableBody displayRowCheckbox={false}>
                    {result}
                </TableBody>
            </Table>
        }

        let imgDialog;
        if (this.state.img !== "") {
            imgDialog = <Lightbox
                mainSrc={this.state.img}
                onCloseRequest={() => this.setState({ img: "" })}
            />
        }
        return (
            <div style={styles.root}>
                <div style={{width: '20%'}}>
                    <Paper style={style}> <List>
                        {this.state.categories}
                    </List>
                    </Paper>
                </div>
                <div style={{width: '80%'}}>
                    {table}
                </div>
                {imgDialog}
            </div>
        )
    }

}