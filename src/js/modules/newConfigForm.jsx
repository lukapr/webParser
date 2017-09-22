import React, {PropTypes} from 'react';
import {ModalContainer, ModalDialog} from 'react-modal-dialog';

class NewConfigForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            formValues: {}
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        let formValues = this.state.formValues;
        let name = event.target.name;
        let value = event.target.value;

        formValues[name] = value;

        this.setState({formValues})
    }

    handleSubmit(event) {
        $.ajax({
            type: "POST",
            url: "/configs",
            data: JSON.stringify({
                "name": this.state.formValues["configname"],
                "description": this.state.formValues["description"],
                "link": this.state.formValues["link"]
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': ' application/json'
            },
            success: function (result) {
                alert('A new config was submitted: ' + result);
                this.props.onClose();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                toastr.error(xhr.responseJSON.message);
            },
            dataType: 'json'
        });

        event.preventDefault();
    }



    render() {
        return (
            {/*<dialog className="mdl-dialog">*/}
            <ModalContainer onClose={this.props.onClose}>
                <ModalDialog onClose={this.props.onClose} width={350} className="example-dialog"
                             dismissOnBackgroundClick={false}>
                    <div className="container">
                        <form onSubmit={this.handleSubmit} className="form-horizontal">
                            <div className="form-group ">
                                <label className="control-label col-sm-2">Name of config: </label>
                                <div className="col-sm-4">
                                    <input type="text" className="form-control" name="configname"
                                           value={this.state.formValues["configname"]}
                                           onChange={this.handleChange}/>
                                </div>
                            </div>
                            <div className="form-group ">
                                <label className="control-label col-sm-2">Description of config: </label>
                                <div className="col-sm-4">
                                    <input type="text" className="form-control" name="description"
                                           value={this.state.formValues["description"]}
                                           onChange={this.handleChange}/>
                                </div>
                            </div>
                            <div className="form-group ">
                                <label className="control-label col-sm-2">Link of config: </label>
                                <div className="col-sm-4">
                                    <input type="text" className="form-control" name="link"
                                           value={this.state.formValues["link"]}
                                           onChange={this.handleChange}/>
                                </div>
                            </div>
                            <div className="form-group">
                                <div className="col-sm-offset-2 col-sm-10">
                                    <button type="submit" className="btn btn-default">Submit</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </ModalDialog>
            </ModalContainer>
        );
    }
}

NewConfigForm.propTypes =  {
    onClose: PropTypes.func,
};

export default NewConfigForm;