USE [master]
GO
/****** Object:  Database [rma]    Script Date: 4/9/2021 7:37:38 PM ******/
CREATE DATABASE [rma]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'rma', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\rma.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'rma_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\rma_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [rma] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [rma].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [rma] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [rma] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [rma] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [rma] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [rma] SET ARITHABORT OFF 
GO
ALTER DATABASE [rma] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [rma] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [rma] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [rma] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [rma] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [rma] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [rma] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [rma] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [rma] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [rma] SET  DISABLE_BROKER 
GO
ALTER DATABASE [rma] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [rma] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [rma] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [rma] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [rma] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [rma] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [rma] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [rma] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [rma] SET  MULTI_USER 
GO
ALTER DATABASE [rma] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [rma] SET DB_CHAINING OFF 
GO
ALTER DATABASE [rma] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [rma] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [rma] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [rma] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [rma] SET QUERY_STORE = OFF
GO
USE [rma]
GO
/****** Object:  DatabaseRole [admins]    Script Date: 4/23/2021 7:07:20 PM ******/
CREATE ROLE [admins]
GO
GRANT ALTER ANY USER TO [admins] AS [dbo]
GO
/****** Object:  DatabaseRole [analysts]    Script Date: 4/23/2021 7:07:20 PM ******/
CREATE ROLE [analysts]
GO
GRANT ALTER ANY USER TO [analysts] AS [dbo]
GO
/****** Object:  DatabaseRole [engineers]    Script Date: 4/23/2021 7:07:20 PM ******/
CREATE ROLE [engineers]
GO
GRANT ALTER ANY USER TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[customerAddresses]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customerAddresses](
	[addressId] [int] NOT NULL,
	[customerId] [int] NOT NULL,
	[businessName] [varchar](50) NOT NULL,
	[address1] [varchar](50) NOT NULL,
	[address2] [varchar](50) NULL,
	[city] [varchar](50) NOT NULL,
	[county] [varchar](50) NULL,
	[stateOrProvince] [varchar](50) NOT NULL,
	[zip] [varchar](50) NOT NULL,
	[country] [varchar](50) NOT NULL,
	[phone] [varchar](50) NOT NULL,
	[fax] [varchar](50) NULL,
 CONSTRAINT [PK_customerAddresses] PRIMARY KEY CLUSTERED 
(
	[addressId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
GRANT SELECT ON [dbo].[customerAddresses] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[customerAddresses] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[customerAddresses] TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[customers]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customers](
	[customerId] [int] NOT NULL,
	[customerName] [varchar](50) NOT NULL,
 CONSTRAINT [PK_customers] PRIMARY KEY CLUSTERED 
(
	[customerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
GRANT SELECT ON [dbo].[customers] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[customers] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[customers] TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[dispositions]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dispositions](
	[dispositionId] [int] NOT NULL,
	[disposition] [varchar](50) NOT NULL,
 CONSTRAINT [PK_dispositions] PRIMARY KEY CLUSTERED 
(
	[dispositionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
GRANT SELECT ON [dbo].[dispositions] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[dispositions] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[dispositions] TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[productCategories]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[productCategories](
	[categoryId] [int] NOT NULL,
	[categoryName] [varchar](50) NOT NULL,
 CONSTRAINT [PK_productCategories] PRIMARY KEY CLUSTERED 
(
	[categoryId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
GRANT SELECT ON [dbo].[productCategories] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[productCategories] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[productCategories] TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[products]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[products](
	[productId] [int] NOT NULL,
	[categoryId] [int] NOT NULL,
	[productName] [varchar](50) NOT NULL,
	[price] [decimal](10, 2) NOT NULL,
 CONSTRAINT [PK_products] PRIMARY KEY CLUSTERED 
(
	[productId] ASC,
	[categoryId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
GRANT SELECT ON [dbo].[products] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[products] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[products] TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[purchaseOrderProducts]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[purchaseOrderProducts](
	[purchaseOrderProductId] [int] NOT NULL,
	[poNumber] [varchar](50) NOT NULL,
	[productId] [int] NOT NULL,
	[categoryId] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[orderDate] [date] NOT NULL,
	[deliverDate] [date] NOT NULL,
 CONSTRAINT [PK_purchaseOrderProducts] PRIMARY KEY CLUSTERED 
(
	[purchaseOrderProductId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
GRANT SELECT ON [dbo].[purchaseOrderProducts] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[purchaseOrderProducts] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[purchaseOrderProducts] TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[purchaseOrders]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[purchaseOrders](
	[poNumber] [varchar](50) NOT NULL,
	[addressId] [int] NOT NULL,
 CONSTRAINT [PK_purchaseOrders] PRIMARY KEY CLUSTERED 
(
	[poNumber] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
GRANT SELECT ON [dbo].[purchaseOrders] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[purchaseOrders] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[purchaseOrders] TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[returnReasonCodes]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[returnReasonCodes](
	[returnReasonCode] [varchar](50) NOT NULL,
	[description] [varchar](50) NOT NULL,
 CONSTRAINT [PK_returnReasonCodes] PRIMARY KEY CLUSTERED 
(
	[returnReasonCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
GRANT SELECT ON [dbo].[returnReasonCodes] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[returnReasonCodes] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[returnReasonCodes] TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[rma]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rma](
	[rmaId] [varchar](50) NOT NULL,
	[owner] [varchar](50) NOT NULL,
	[lastModified] [datetime] NOT NULL,
	[lastModifiedBy] [varchar](50) NOT NULL,
	[statusId] [int] NOT NULL,
 CONSTRAINT [PK_rma] PRIMARY KEY CLUSTERED 
(
	[rmaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
GRANT DELETE ON [dbo].[rma] TO [admins] AS [dbo]
GO
GRANT INSERT ON [dbo].[rma] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[rma] TO [admins] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rma] TO [admins] AS [dbo]
GO
GRANT DELETE ON [dbo].[rma] TO [analysts] AS [dbo]
GO
GRANT INSERT ON [dbo].[rma] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[rma] TO [analysts] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rma] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[rma] TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rma] ([lastModified]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rma] ([lastModifiedBy]) TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[rmaDetails]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rmaDetails](
	[rmaId] [varchar](50) NOT NULL,
	[created] [datetime] NOT NULL,
	[createdBy] [varchar](50) NOT NULL,
	[returnReasonCode] [varchar](50) NOT NULL,
	[creditReplaceRepair] [varchar](50) NOT NULL,
	[purchaseOrderProductId] [int] NOT NULL,
	[returnQuantity] [int] NOT NULL,
	[returnLabelTracker] [varchar](50) NULL,
	[additionalInfo] [varchar](50) NULL,
	[poNumber] [varchar](50) NOT NULL,
	[initialEvaluation] [varchar](max) NULL,
	[engineeringEvaluation] [varchar](max) NULL,
	[dispositionId] [int] NULL,
	[dispositionNotes] [varchar](max) NULL,
	[replacementTrackingNumber] [varchar](50) NULL,
	[replacementShipDate] [date] NULL,
	[shipReplacementRepair] [bit] NOT NULL,
 CONSTRAINT [PK_rmaDetails] PRIMARY KEY CLUSTERED 
(
	[rmaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
GRANT DELETE ON [dbo].[rmaDetails] TO [admins] AS [dbo]
GO
GRANT INSERT ON [dbo].[rmaDetails] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[rmaDetails] TO [admins] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] TO [admins] AS [dbo]
GO
GRANT DELETE ON [dbo].[rmaDetails] TO [analysts] AS [dbo]
GO
GRANT INSERT ON [dbo].[rmaDetails] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[rmaDetails] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[rmaDetails] TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([rmaId]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([rmaId]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([created]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([created]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([createdBy]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([createdBy]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([returnReasonCode]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([returnReasonCode]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([creditReplaceRepair]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([creditReplaceRepair]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([purchaseOrderProductId]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([purchaseOrderProductId]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([returnQuantity]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([returnQuantity]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([returnLabelTracker]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([returnLabelTracker]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([additionalInfo]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([additionalInfo]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([poNumber]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([poNumber]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([initialEvaluation]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([initialEvaluation]) TO [engineers] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([engineeringEvaluation]) TO [analysts] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([engineeringEvaluation]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([dispositionId]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([dispositionId]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([dispositionNotes]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([dispositionNotes]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([replacementTrackingNumber]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([replacementTrackingNumber]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([replacementShipDate]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([replacementShipDate]) TO [engineers] AS [dbo]
GO
GRANT UPDATE ON [dbo].[rmaDetails] ([shipReplacementRepair]) TO [analysts] AS [dbo]
GO
DENY UPDATE ON [dbo].[rmaDetails] ([shipReplacementRepair]) TO [engineers] AS [dbo]
GO
/****** Object:  Table [dbo].[rmaStatuses]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rmaStatuses](
	[statusId] [int] NOT NULL,
	[description] [varchar](50) NOT NULL,
 CONSTRAINT [PK_rmaStatuses] PRIMARY KEY CLUSTERED 
(
	[statusId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
GRANT SELECT ON [dbo].[rmaStatuses] TO [admins] AS [dbo]
GO
GRANT SELECT ON [dbo].[rmaStatuses] TO [analysts] AS [dbo]
GO
GRANT SELECT ON [dbo].[rmaStatuses] TO [engineers] AS [dbo]
GO
ALTER TABLE [dbo].[customerAddresses]  WITH CHECK ADD  CONSTRAINT [FK_customerAddresses_customers] FOREIGN KEY([customerId])
REFERENCES [dbo].[customers] ([customerId])
GO
ALTER TABLE [dbo].[customerAddresses] CHECK CONSTRAINT [FK_customerAddresses_customers]
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD  CONSTRAINT [FK_products_productCategories] FOREIGN KEY([categoryId])
REFERENCES [dbo].[productCategories] ([categoryId])
GO
ALTER TABLE [dbo].[products] CHECK CONSTRAINT [FK_products_productCategories]
GO
ALTER TABLE [dbo].[purchaseOrderProducts]  WITH CHECK ADD  CONSTRAINT [FK_purchaseOrderProducts_products] FOREIGN KEY([productId], [categoryId])
REFERENCES [dbo].[products] ([productId], [categoryId])
GO
ALTER TABLE [dbo].[purchaseOrderProducts] CHECK CONSTRAINT [FK_purchaseOrderProducts_products]
GO
ALTER TABLE [dbo].[purchaseOrderProducts]  WITH CHECK ADD  CONSTRAINT [FK_purchaseOrderProducts_purchaseOrders] FOREIGN KEY([poNumber])
REFERENCES [dbo].[purchaseOrders] ([poNumber])
GO
ALTER TABLE [dbo].[purchaseOrderProducts] CHECK CONSTRAINT [FK_purchaseOrderProducts_purchaseOrders]
GO
ALTER TABLE [dbo].[purchaseOrders]  WITH CHECK ADD  CONSTRAINT [FK_purchaseOrders_customerAddresses] FOREIGN KEY([addressId])
REFERENCES [dbo].[customerAddresses] ([addressId])
GO
ALTER TABLE [dbo].[purchaseOrders] CHECK CONSTRAINT [FK_purchaseOrders_customerAddresses]
GO
ALTER TABLE [dbo].[rma]  WITH CHECK ADD  CONSTRAINT [FK_rma_rmaStatuses] FOREIGN KEY([statusId])
REFERENCES [dbo].[rmaStatuses] ([statusId])
GO
ALTER TABLE [dbo].[rma] CHECK CONSTRAINT [FK_rma_rmaStatuses]
GO
ALTER TABLE [dbo].[rmaDetails]  WITH CHECK ADD  CONSTRAINT [FK_rmaDetails_dispositions] FOREIGN KEY([dispositionId])
REFERENCES [dbo].[dispositions] ([dispositionId])
GO
ALTER TABLE [dbo].[rmaDetails] CHECK CONSTRAINT [FK_rmaDetails_dispositions]
GO
ALTER TABLE [dbo].[rmaDetails]  WITH CHECK ADD  CONSTRAINT [FK_rmaDetails_purchaseOrderProducts] FOREIGN KEY([purchaseOrderProductId])
REFERENCES [dbo].[purchaseOrderProducts] ([purchaseOrderProductId])
GO
ALTER TABLE [dbo].[rmaDetails] CHECK CONSTRAINT [FK_rmaDetails_purchaseOrderProducts]
GO
ALTER TABLE [dbo].[rmaDetails]  WITH CHECK ADD  CONSTRAINT [FK_rmaDetails_purchaseOrders] FOREIGN KEY([poNumber])
REFERENCES [dbo].[purchaseOrders] ([poNumber])
GO
ALTER TABLE [dbo].[rmaDetails] CHECK CONSTRAINT [FK_rmaDetails_purchaseOrders]
GO
ALTER TABLE [dbo].[rmaDetails]  WITH CHECK ADD  CONSTRAINT [FK_rmaDetails_returnReasonCodes] FOREIGN KEY([returnReasonCode])
REFERENCES [dbo].[returnReasonCodes] ([returnReasonCode])
GO
ALTER TABLE [dbo].[rmaDetails] CHECK CONSTRAINT [FK_rmaDetails_returnReasonCodes]
GO
ALTER TABLE [dbo].[rmaDetails]  WITH CHECK ADD  CONSTRAINT [FK_rmaDetails_rma] FOREIGN KEY([rmaId])
REFERENCES [dbo].[rma] ([rmaId])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[rmaDetails] CHECK CONSTRAINT [FK_rmaDetails_rma]
GO
/****** Object:  StoredProcedure [dbo].[GetNextRMAId]    Script Date: 4/23/2021 7:07:21 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Ryan McAllister-Grum
-- Create date: 03/31/2021
-- Description:	GetNextRMAId calculates the next RMA ID
--              that will be inserted into rma.
-- =============================================
CREATE     procedure [dbo].[GetNextRMAId] (@nextRMAId varchar(50) output, @useThisRMAId varchar(50) = '')
AS
BEGIN
	declare @currRMAId varchar(50);
	if @useThisRMAId <> ''
		set @currRMAId = @useThisRMAId;
	else
		set @currRMAId = (select isnull(max(rmaId), '') from rma);

	declare @arr table (
		i int,
		val char(1)
	);
	insert into @arr (i, val)
	values (0, '0'), (1, '1'), (2, '2'), (3, '3'), (4, '4'),
		   (5, '5'), (6, '6'), (7, '7'), (8, '8'), (9, '9'),
		   (10, 'a'), (11, 'b'), (12, 'c'), (13, 'd'), (14, 'e'),
		   (15, 'f'), (16, 'g'), (17, 'h'), (18, 'i'), (19, 'j'),
		   (20, 'k'), (21, 'l'), (22, 'm'), (23, 'n'), (24, 'o'),
		   (25, 'p'), (26, 'q'), (27, 'r'), (28, 's'), (29, 't'),
		   (30, 'u'), (31, 'v'), (32, 'w'), (33, 'x'), (34, 'y'),
		   (35, 'z'), (36, 'A'), (37, 'B'), (38, 'C'), (39, 'D'),
		   (40, 'E'), (41, 'F'), (42, 'G'), (43, 'H'), (44, 'I'),
		   (42, 'J'), (43, 'K'), (44, 'L'), (45, 'M'), (46, 'N'),
		   (47, 'O'), (48, 'P'), (49, 'Q'), (50, 'R'), (51, 'S'),
		   (52, 'T'), (53, 'U'), (54, 'V'), (55, 'W'), (56, 'X'),
		   (57, 'Y'), (58, 'Z')
	;

	if @currRMAId = ''
	   set @nextRMAId = '0';
	else
	begin
		declare @len int;
		set @len = (select len(@currRMAId));

		declare @maxRMAId varchar(50);
		set @maxRMAId = (select replicate('Z', 50));

		declare @currMaxRMAId varchar(50);
		set @currMaxRMAId = (select replicate('Z', @len));

		-- First check if we reached the last possible ID.
		if @currRMAId = @maxRMAId
			RAISERROR('Error while inserting RMA! Reached end of IDs!', 16, 1);
		else if @currRMAId = @currMaxRMAId -- Then check if we reached the largest ID of the current length.
			set @nextRMAId = (select '0' + REPLICATE('0', @len));
		else -- Increment to the next ID.
		begin
			declare @pos int;
			set @pos = @len;
			while (@pos > 0)
			begin
				declare @currChar char;
				set @currChar = (select substring(@currRMAId, @pos, 1));

				if @currChar <> 'Z'
				begin
					declare @nextChar char;
					set @nextChar = (select val from @arr where i = (select i + 1 from @arr where val = @currChar));
					set @nextRMAId = (select substring(@currRMAId, 1, @pos - 1) + @nextChar + substring(@currRMAId, @pos + 1, @len));
					set @pos = 0;
				end
				else
				begin
					declare @subPos int;
					set @subPos = @pos;
					while (@currChar = 'Z')
					begin
						set @subPos = @subPos - 1;
						set @currChar = (select substring(@currRMAId, @subPos, 1));
					end

					set @nextChar = (select val from @arr where i = (select i + 1 from @arr where val = @currChar));
					set @nextRMAId = (select substring(@currRMAId, 1, @subPos - 1) + @nextChar + replicate('0', @len - (@subPos)));
					set @pos = 0;
				end
				set @pos = @pos - 1;
			end
		end
	end
	delete from @arr;
END
GO
GRANT EXECUTE ON [dbo].[GetNextRMAId] TO [admins] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GetNextRMAId] TO [analysts] AS [dbo]
GO
