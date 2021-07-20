<template>
	<div>
		<quill-editor v-model="content" ref="myQuillEditor" :options="editorOption" >
		</quill-editor>
	</div>
</template>
<script>
	import 'quill/dist/quill.snow.css';
	import {
		Quill
	} from 'vue-quill-editor'
	import {
		container,
		ImageExtend,
		QuillWatch
	} from 'quill-image-extend-module'
	import ImageResize from 'quill-image-resize-module';
	Quill.register('modules/ImageExtend', ImageExtend);
	// use resize module
	Quill.register('modules/ImageResize', ImageResize);
	export default {
		name: "editorComponent",
		data() {
			return {
				loading: true,
				content: '',
				editorOption: {
					modules: {
						ImageResize: {},
						ImageExtend: {
							loading: true,
							name: 'file',
							action: `${this.$api.uploadUrl}?projectId=projectId&typeId=$typeId`,
							response: (res) => {
								return `${this.$api.getFileUrl}?fileId=${res.fileId}&fileType=img&imgType=1`;
							}
						},
						toolbar: { // 如果不上传图片到服务器，此处不必配置
							container: container, // container为工具栏，此次引入了全部工具栏，也可自行配置
							handlers: {
								'image': function() { // 劫持原来的图片点击按钮事件
									QuillWatch.emit(this.quill.id)
								}
							}
						}
					},
					theme: 'snow'
				}
			}
		},
		computed: {
			editor() {
				return this.$refs.myQuillEditor.quill;
			},
		},
		watch: {
			'content': function() {
				this.$emit('text', this.content)
			}
		}
	};
</script>
<style>
	.ql-snow .ql-picker.ql-size .ql-picker-label::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item::before {
		content: "14px";
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
		content: "10px";
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
		content: "18px";
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
		content: "32px";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item::before {
		content: "文本";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
		content: "标题1";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
		content: "标题2";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
		content: "标题3";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
		content: "标题4";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
		content: "标题5";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
		content: "标题6";
	}

	.ql-snow .ql-picker.ql-font .ql-picker-label::before,
	.ql-snow .ql-picker.ql-font .ql-picker-item::before {
		content: "标准字体";
	}

	.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
	.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
		content: "衬线字体";
	}

	.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
	.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
		content: "等宽字体";
	}
</style>
