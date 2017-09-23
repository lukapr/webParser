import React, {PropTypes} from 'react';
import {Dialog, Textfield, DialogActions, DialogContent, Button} from 'react-mdl';

import request from 'superagent';
import {notify} from 'react-notify-toast';

class NewConfigForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            description: "",
            link: ""
        };
        this.handleOpenDialog = this.handleOpenDialog.bind(this);
        this.handleCloseDialog = this.handleCloseDialog.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleOpenDialog() {
        this.setState({
            openDialog: true
        });
    }

    handleCloseDialog() {
        this.setState({
            openDialog: false
        });
        this.props.onClose();
    }

    handleSubmit(event) {
        // $.ajax({
        //     type: "POST",
        //     url: "/configs",
        //     data: JSON.stringify({
        //         "name": this.state.name,
        //         "description": this.state.description,
        //         "link": this.state.link
        //     }),
        //     headers: {
        //         'Accept': 'application/json',
        //         'Content-Type': ' application/json'
        //     },
        //     success: function (result) {
        //         alert('A new config was submitted: ' + result);
        //         this.handleCloseDialog();
        //     },
        //     error: function (xhr, ajaxOptions, thrownError) {
        //         // toastr.error(xhr.responseJSON.message);
        //         this.handleCloseDialog();
        //     },
        //     dataType: 'json'
        // });
        request.delete("configs/")
            .end(function(err, res){
                if (err || !res.ok) {
                    notify.show('Oh no! error', "error");
                } else {
                    notify.show('yay got ' + JSON.stringify(res.body), "success");
                }
            });
        event.preventDefault();
    };


    render() {
        return (
            <div>
                <Button colored onClick={this.handleOpenDialog} raised ripple>Add new</Button>
                <Dialog open={this.state.openDialog}>
                    <DialogContent>
                        <Textfield
                            onChange={(event) => {
                                this.setState({name: event.target.value})
                            }}
                            label="Name..."
                            floatingLabel
                            // style={{width: '200px'}}
                        />
                        <Textfield
                            onChange={(event) => {
                                this.setState({description: event.target.value})
                            }}
                            label="Description..."
                            floatingLabel
                            // style={{width: '200px'}}
                        />
                        <Textfield
                            onChange={(event) => {
                                this.setState({link: event.target.value})
                            }}
                            label="Link..."
                            floatingLabel
                            // style={{width: '200px'}}
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button type='button' onClick={this.handleSubmit}>Submit</Button>
                        <Button type='button' onClick={this.handleCloseDialog}>Cancel</Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
};

NewConfigForm.propTypes = {
    onClose: PropTypes.func,
};

export default NewConfigForm;